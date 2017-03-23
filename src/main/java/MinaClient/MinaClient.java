package MinaClient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	
	private SocketConnector connector;
    private ConnectFuture future;
    private IoSession session;

    public boolean connect() {
        /*
         * 1.����һ��socket����,���ӵ�������
         */
        connector = new NioSocketConnector();

        /*
         * ��ȡ��������,������ӹ�����
         */
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();

        // b.�����־������
        chain.addLast("logger", new LoggingFilter());

        // c.����ַ�ı��������
        chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        /*
         * 3.������Ϣ�����������ڴ�����յ�����Ϣ
         */
        connector.setHandler(new MsgHanler());

        /*
         * 4.���IP�Ͷ˿ں����ӵ�������
         */
        future = connector.connect(new InetSocketAddress("127.0.0.1", 8888));
        // �ȴ����Ӵ������
        future.awaitUninterruptibly();

        /*
         * 5.��ȡsession����,ͨ��session�����������������Ϣ��
         */
        session = future.getSession();
        session.getConfig().setUseReadOperation(true);
        return future.isConnected();
    }

    /**
     * �������������Ϣ
     * 
     * @param message
     */
    public void sendMsg2Server(String message) {
        session.write(message);
    }

    /**
     * �ر��������������
     * 
     * @return
     */
    public boolean close() {
        CloseFuture future = session.getCloseFuture();
        future.awaitUninterruptibly(1000);
        connector.dispose();
        return true;
    }
}
