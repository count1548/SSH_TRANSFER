import com.chilkatsoft.*;

public class SSH_transfer extends CkSsh{
	private String hostname; 
	private int port;
	boolean connect;
	final String remotepath = "/home/mainuser/temp/";
	
	public SSH_transfer() {
		connect = false;
		if(!this.UnlockComponent("30-day trial")) {
			System.err.println(this.lastErrorText()); return;
		}
	}
	public SSH_transfer(String hostname, int port) {
		this();
		connect = connectSSH(hostname, port);
	}
	public boolean connectSSH(String hostname, int port) {
		this.hostname = hostname; this.port = port;
		if(!this.Connect(hostname, port)) {
			System.err.println(this.lastErrorText()); return false;
		}
		this.put_IdleTimeoutMs(5000);
		return true;
	}
	public boolean loginServer(String name, String password) {
		if(!this.AuthenticatePw(name, password) || !connect) {
			System.err.println(this.lastErrorText()); return false;
		}
		createScp();
		return true;
	}
	private CkScp scp = new CkScp();
	private boolean createScp() {
		if(!scp.UseSsh(this) || !connect) {
			System.err.println(this.lastErrorText()); return false;
		}
		return true;
	}
	public boolean transferFile(String path, String filename) {
		if(!scp.UploadFile(path + filename, remotepath + filename) || !connect) {
			System.err.println(this.lastErrorText()); return false;
		}
		return true;
	}
}
