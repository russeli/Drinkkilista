package kayttoliittyma;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExtensionFilter extends FileFilter {

    private String description;
    private String extension;

    public ExtensionFilter(String desc, String ext) {
        description = desc;
        extension = ext;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String path = file.getAbsolutePath();
        String ext = extension;
        if (path.endsWith(ext) && (path.charAt(path.length() - ext.length()) == '.')) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return (description == null ? extension : description);
    }
}