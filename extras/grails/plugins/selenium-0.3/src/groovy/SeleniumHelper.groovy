class SeleniumHelper {
    private File baseDir

    SeleniumHelper(File baseDir) {
        this.baseDir = baseDir
    }

    /**
     * Returns a list of all the suites (directories) under the given base directory.
     */
    def findAllSuites(File dir, def suites) {
            def files = dir.listFiles()
            files.each() { file ->
                    if(acceptDirectory(file)) {
                            suites << getFileName(file, baseDir)
                            findAllSuites(file, suites)
                    }
            }
    }

    /**
     * Returns a list of all the paths (relative to the base directory) to files 
     * beneath the given directory. 
     */
    def findTests(File dir, def tests, boolean all) {
            def files = dir.listFiles()
            files.each() { file ->
                    if(acceptDirectory(file)) {
                            if(all) {
                                    findTests(file, tests, all)
                            }
                    }
                    if(acceptFile(file)) {
                            tests << getFileName(file, baseDir)
                    }
            }
    }	

    /**
     * Returns a collection where each item is a line from the file
     */
    def fileToLines(File file) {
            validateFile(file)
            def lines = []
            file.eachLine { line -> 
                    // check line is not blank
                    if(line.trim()) {
                            lines << line
                    }
            }
            return lines
    }

    /**
     * Throws a RuntimeException if the given file is not contained somewhere beneath 
     * the base directory.
     */
    protected void validateFile(File file) {
        if(!file.getCanonicalPath().startsWith(baseDir.getCanonicalPath())) {
            throw new RuntimeException("Illegal file access. All test files must be located with the /selenium/tests/ directory in the web application.")
        }
    }

    protected String getFileName(File file, File baseDir) {
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()+1)
    }

    protected Boolean acceptDirectory(File file) {
        return (file.isDirectory() && !(file.isHidden() || file.name.startsWith('.')))
    }

    protected Boolean acceptFile(File file) {
        return file.isFile()
    }


}