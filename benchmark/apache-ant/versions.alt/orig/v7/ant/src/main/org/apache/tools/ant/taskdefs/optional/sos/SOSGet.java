/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Ant" and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.tools.ant.taskdefs.optional.sos;

import org.apache.tools.ant.types.Commandline;

/**
 * Retrieves a read-only copy of the specified project or file
 * from Visual SourceSafe via a SourceOffSite server.
 *
 * @author    Jesse Stockall
 *
 * @ant.task name="sosget" category="scm"
 */
public class SOSGet extends SOS {

    /**
     * The Filename to act upon.
     * If no file is specified then the tasks
     * act upon the project.
     *
     * @param  filename  The new file value
     */
    public final void setFile(String filename) {
        super.setInternalFilename(filename);
    }

    /**
     * Flag to recursively apply the action. Defaults to false
     *
     * @param  recursive  True for recursive operation.
     */
    public void setRecursive(boolean recursive) {
        super.setInternalRecursive(recursive);
    }

    /**
     * Set the version number to get -
     * only works with SOSGet on a file.
     *
     * @param  version  The new version value
     */
    public void setVersion(String version) {
        super.setInternalVersion(version);
    }

    /**
     * The labeled version to operate on in SourceSafe.
     *
     * @param  label  The new label value
     */
    public void setLabel(String label) {
        super.setInternalLabel(label);
    }

    /**
     * Build the command line <br>
     *
     * GetFile required parameters: -server -name -password -database -project -file<br>
     * GetFile optional parameters: -workdir -revision -verbose -nocache -nocompression -soshome<br>
     *
     * GetProject required parameters: -server -name -password -database -project<br>
     * GetProject optional parameters: -label -workdir -recursive -verbose -nocache
     * -nocompression -soshome<br>
     *
     * @return    Commandline the generated command to be executed
     */
    protected Commandline buildCmdLine() {
        commandLine = new Commandline();

        // If we find a "file" attribute then act on a file otherwise act on a project
        if (getFilename() != null) {
            // add -command GetFile to the commandline
            commandLine.createArgument().setValue(SOSCmd.FLAG_COMMAND);
            commandLine.createArgument().setValue(SOSCmd.COMMAND_GET_FILE);
            // add -file xxxxx to the commandline
            commandLine.createArgument().setValue(SOSCmd.FLAG_FILE);
            commandLine.createArgument().setValue(getFilename());
            // look for a version attribute
            if (getVersion() != null) {
                //add -revision xxxxx to the commandline
                commandLine.createArgument().setValue(SOSCmd.FLAG_VERSION);
                commandLine.createArgument().setValue(getVersion());
            }
        } else {
            // add -command GetProject to the commandline
            commandLine.createArgument().setValue(SOSCmd.FLAG_COMMAND);
            commandLine.createArgument().setValue(SOSCmd.COMMAND_GET_PROJECT);
            // look for a recursive option
            commandLine.createArgument().setValue(getRecursive());
            // look for a label option
            if (getLabel() != null) {
                commandLine.createArgument().setValue(SOSCmd.FLAG_LABEL);
                commandLine.createArgument().setValue(getLabel());
            }
        }

        getRequiredAttributes();
        getOptionalAttributes();

        return commandLine;
    }
}
