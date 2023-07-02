

package netbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CoalesceKeyvalue extends Task {
    private String property;

    public void setProperty(String property) {
        this.property = property;
    }

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    private String valueSep;

    public void setValueSep(String valueSep) {
        this.valueSep = valueSep;
    }

    private String entrySep;

    public void setEntrySep(String entrySep) {
        this.entrySep = entrySep;
    }

    private String multiSep;

    public void setMultiSep(String multiSep) {
        this.multiSep = multiSep;
    }

    private String outSep;

    public void setOutSep(String outSep) {
        this.outSep = outSep;
    }

    @Override
    public void execute() throws BuildException {
        List<String> result = new ArrayList<>();
        Map<String, List<String>> module2Paths = new HashMap<>();

        for (String entry : value.split(Pattern.quote(entrySep))) {
            String[] keyValue = entry.split(Pattern.quote(valueSep), 2);
            if (keyValue.length == 1) {
                result.add(keyValue[0]);
            } else {
                module2Paths.computeIfAbsent(keyValue[0], s -> new ArrayList<>())
                            .add(keyValue[1].trim());
            }
        }
        module2Paths.entrySet()
                    .stream()
                    .forEach(e -> result.add(e.getKey() + valueSep + e.getValue().stream().collect(Collectors.joining(multiSep))));
        getProject().setProperty(property, result.stream().collect(Collectors.joining(" " + entrySep)));
    }

}

                