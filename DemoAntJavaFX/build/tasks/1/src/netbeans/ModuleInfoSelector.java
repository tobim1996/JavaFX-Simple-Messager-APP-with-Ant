
            
package netbeans;

import java.io.File;
import java.util.Arrays;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.selectors.BaseExtendSelector;

public class ModuleInfoSelector extends BaseExtendSelector {

    @Override
    public boolean isSelected(File basedir, String filename, File file) throws BuildException {
        String extension = Arrays.stream(getParameters())
                                 .filter(p -> "extension".equals(p.getName()))
                                 .map(p -> p.getValue())
                                 .findAny()
                                 .get();
        return !new File(file, "module-info." + extension).exists();
    }

}

                