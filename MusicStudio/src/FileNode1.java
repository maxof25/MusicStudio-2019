import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileNode1 {

        private File file;

        public FileNode1(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }


