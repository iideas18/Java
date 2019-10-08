import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

class RpcImporter<S> {
    S importer(final Class<?> serviceClass, final InetSocketAddress addr) {

        return (S) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[] { serviceClass.getInterfaces()[0] },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = null;
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;

                        try {
                            socket = new Socket();
                            socket.connect(addr);
                            output = new ObjectOutputStream(socket.getOutputStream());
                            output.writeUTF(serviceClass.getName());
                            output.writeUTF(method.getName());
                            output.writeObject(method.getParameterTypes());
                            output.writeObject(args);
                            input = new ObjectInputStream(socket.getInputStream());//同步阻塞等待
                        } finally {
                            if( socket != null ) {
                                socket = null;
                            }
                            if( output != null ) {
                                output = null;
                            }
                            if( input != null ) {
                                input = null;
                            }
                        }
                        return input.readObject();
                    }
                });
    }
}