public class ShellCommandOutput {

    private String output;
    private String error;

    ShellCommandOutput(String output, String error) {
        this.output = output;
        this.error = error;
    }

    String getOutput() {
        return output;
    }

    String getError() {
        return error;
    }
}
