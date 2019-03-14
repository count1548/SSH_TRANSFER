import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.file.*;

public class fWatcher extends Thread {
	Path _path;
	String eventFName;
	private WatchService wService = null;
	private WatchKey wKey = null;
	
	private boolean stop;
	
	public fWatcher(String path) throws IOException {
		_path = Paths.get(path);
		wService = this._path.getFileSystem().newWatchService();
		_path.register(wService, StandardWatchEventKinds.ENTRY_MODIFY);
		System.out.println(this.isDaemon());
	}

	public void run() {
		if(!transition_file.ssh.connect) return;
		while(!stop) {
			/*
			if(wKey!=null) wKey.cancel();;
			try { wKey = wService.poll ((long) 1, TimeUnit.SECONDS); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println("wating change");
			if(wKey == null) continue;
			System.out.println("get event change");
			List<WatchEvent<?>> events = wKey.pollEvents();

			for (WatchEvent<?> event : events) {
	            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
	            	eventFName = event.context().toString();
	            	actionFunction();
	            }
	        }
	    	if(!wKey.reset()) {
                try { wService.close(); } 
                catch (IOException e) { e.printStackTrace(); }
            }*/
			System.out.println("wait");
		}
	}
	public void actionFunction() { }
	public void stopThread() {
		stop = true;
		if(wKey != null) {
			if(!wKey.reset()) {
                try { wService.close(); } 
                catch (IOException e) { e.printStackTrace(); }
            }
			wKey.cancel();
		}
	}

}

