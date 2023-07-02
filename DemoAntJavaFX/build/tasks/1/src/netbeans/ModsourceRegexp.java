
            
package netbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ModsourceRegexp extends Task {
    private String property;

    public void setProperty(String property) {
        this.property = property;
    }

    private String filePattern;

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    private String modsource;

    public void setModsource(String modsource) {
        this.modsource = modsource;
    }

    private List<String> expandGroup(String grp) {
        List<String> exp = new ArrayList<>();
        String item = "";
        int depth = 0;

        for (int i = 0; i < grp.length(); i++) {
            char c = grp.charAt(i);
            switch (c) {
                case '{':
                    if (depth++ == 0) {
                        continue;
                    }
                    break;
                case '}':
                    if (--depth == 0) {
                        exp.add(item);
                        continue;
                    }
                    break;
                case ',':
                    if (depth == 1) {
                        exp.add(item);
                        item = "";
                        continue;
                    }
                default:
                    break;
            }
            item = item + c;
        }
        return exp;
    }

    private List<String> pathVariants(String spec) {
        return pathVariants(spec, new ArrayList<>());
    }

    private List<String> pathVariants(String spec, List<String> res) {
        int start  = spec.indexOf('{');
        if (start == -1) {
            res.add(spec);
            return res;
        }
        int depth = 1;
        int end;
        for (end = start + 1; end < spec.length() && depth > 0; end++) {
            char c = spec.charAt(end);
            switch (c) {
                case '{': depth++; break;
                case '}': depth--; break;
            }
        }
        String prefix = spec.substring(0, start);
        String suffix = spec.substring(end);
        expandGroup(spec.substring(start, end)).stream().forEach(item -> {
            pathVariants(prefix + item + suffix, res);
        });
        return res;
    }

    private String toRegexp2(String spec, String filepattern, String separator) {
        List<String> prefixes = new ArrayList<>();
        List<String> suffixes = new ArrayList<>();
        pathVariants(spec).forEach(item -> {
            suffixes.add(item);
        });
        String tail = "";
        String separatorString = separator;
        if ("\\".equals(separatorString)) {
            separatorString = "\\\\";
        }
        if (filepattern != null && !Objects.equals(filepattern, tail)) {
            tail = separatorString + filepattern;
        }
        return "([^" + separatorString +"]+)\\Q" + separator + "\\E(" + suffixes.stream().collect(Collectors.joining("|")) + ")" + tail;
    }

    @Override
    public void execute() throws BuildException {
        getProject().setProperty(property, toRegexp2(modsource, filePattern, getProject().getProperty("file.separator")));
    }

}

                