package bestcoders.library.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.inventory.Inventory;

public class LibraryServer {

    final Library library;
    final int portNumber;

    boolean okToRun = true;

    public LibraryServer(final BusinessDate bd, final Inventory i) {
	library = new Library(bd, i);
	portNumber = 40_000;
    }

    public void start() {

	ServerSocket serverSocket;
	try {
	    while (okToRun) {
		serverSocket = new ServerSocket(portNumber);
		final Socket clientSocket = serverSocket.accept();
	    }
	} catch (final IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void stop() {
	okToRun = false;
    }

}
