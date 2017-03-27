import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ShellCommand {

    private String command;
    private boolean waitForFinish;
    private StringBuilder stdOutput;
    private StringBuilder stdError;

    ShellCommand(String command, boolean waitForFinish) {
        this.command = command;
        this.waitForFinish = waitForFinish;
    }

    ShellCommandOutput execute() throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        if (waitForFinish) {
            stdOutput = new StringBuilder();
            stdError = new StringBuilder();
            BufferedReader stdInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErrorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = stdInputStream.readLine()) != null) {
                stdOutput.append(s);
            }
            while ((s = stdErrorStream.readLine()) != null) {
                stdError.append(s);
            }
            return new ShellCommandOutput(stdOutput.toString(), stdError.toString());
        } else {
            return null;
        }
    }
}
