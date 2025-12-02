package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final ResponseHandler responseHandler;
    private Socket connection;

    public static final String SPACE = " ";
    public static final String WEBAPP = "./webapp";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.responseHandler = new ResponseHandler();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            ArrayList<String> headers = parseHeader(reader);
            String resource = getResourceByRequestHeaderFirstLine(headers);

            DataOutputStream dos = new DataOutputStream(out);
            Path path = new File(WEBAPP + resource).toPath();
            log.debug("Resource Path: {}", path);

            byte[] body = Files.readAllBytes(path);
            responseHandler.response200Header(dos, body);
            responseHandler.responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static String getResourceByRequestHeaderFirstLine(ArrayList<String> headers) {
        String[] tokens = headers.getFirst().split(SPACE);
        String url = tokens[1];
        log.debug("Request Rescoure: {}", url);
        return url;
    }

    private static ArrayList<String> parseHeader(BufferedReader reader) throws IOException {
        ArrayList<String> headers = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }

            headers.add(line);
            log.debug("header: {}", line);
        }
        return headers;
    }
}