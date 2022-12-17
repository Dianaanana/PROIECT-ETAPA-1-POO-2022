import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import server.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

    /**
     *
     */
    private Main() {
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        // old main
        File directory = new File("checker/resources/in/");
        Path path = Paths.get("checker/resources/out/");
        Files.createDirectories(path);

        File file = new File(args[0]);
        String filepath = "checker/resources/out/out_" + file.getName();
        File out = new File(filepath);
        out.delete();
        boolean isCreated = out.createNewFile();
        if (isCreated) {
            action(file.getName(), filepath, args[1]);
        }
    }

    /**
     *
     * @param fileName
     * @param outputFilePath
     * @param outputFilePath2
     * @throws IOException
     */
    public static void action(final String fileName,
                              final String outputFilePath,
                              final String outputFilePath2
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // input data are tot file ul in el ca si un string
        Input inputData = objectMapper.readValue(
                new File("checker/resources/in/" + fileName),
                Input.class
        );

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation
                Server server = new Server();
                server.serverRun(inputData, output);
        //TODO end of implementation

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(outputFilePath), output);
        objectWriter.writeValue(new File(outputFilePath2), output);
    }
}
