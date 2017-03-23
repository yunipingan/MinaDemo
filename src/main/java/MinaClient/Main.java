package MinaClient;

public class Main {

	static MinaClient client;
	static int msg = 0;
	
    public static void main(String[] args) {
        client= new MinaClient();
        client.connect();
        client.sendMsg2Server("message from cilent");
        Main main = new Main();
        new Thread(new Runnable() {
			
        	public void run() {
        		while(true){
        			try {
        				Thread.sleep(2000);
        				client.sendMsg2Server(""+msg++);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		}
        	}
		}).start();
    }

}