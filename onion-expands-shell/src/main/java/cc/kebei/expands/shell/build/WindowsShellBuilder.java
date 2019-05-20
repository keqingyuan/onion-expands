package cc.kebei.expands.shell.build;

import cc.kebei.expands.shell.Shell;

/**
 * Created by Kebei on 16-6-28.
 */
public class WindowsShellBuilder extends AbstractShellBuilder {

    @Override
    public Shell buildTextShell(String text) throws Exception {
        String file = createFile(text);
        return Shell.build(file);
    }

}
