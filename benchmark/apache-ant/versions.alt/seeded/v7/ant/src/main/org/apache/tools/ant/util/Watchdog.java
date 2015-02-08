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

package org.apache.tools.ant.util;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Generalization of <code>ExecuteWatchdog</code>
 *
 * @since Ant 1.5
 *
 * @see org.apache.tools.ant.taskdefs.ExecuteWatchdog
 *
 * @author Stefan Bodewig
 * @author thomas.haas@softwired-inc.com
 * @author <a href="mailto:sbailliez@imediation.com">Stephane Bailliez</a>
 */
public class Watchdog implements Runnable {

    private Vector observers = new Vector(1);
    private long timeout = -1;
    private boolean stopped = false;

    public Watchdog(long timeout) {
        if (timeout < 1) {
            throw new IllegalArgumentException("timeout lesser than 1.");
        }
        this.timeout = timeout;
    }

    public void addTimeoutObserver(TimeoutObserver to) {
        observers.addElement(to);
    }

    public void removeTimeoutObserver(TimeoutObserver to) {
        observers.removeElement(to);
    }

    protected final void fireTimeoutOccured() {
        Enumeration e = observers.elements();
        while (e.hasMoreElements()) {
            ((TimeoutObserver) e.nextElement()).timeoutOccured(this);
        }
    }

    public synchronized void start() {
        stopped = false;
        Thread t = new Thread(this, "WATCHDOG");
        t.setDaemon(true);
        t.start();
    }

    public synchronized void stop() {
        stopped = true;
        notifyAll();
    }

    public synchronized void run() {
        final long until = System.currentTimeMillis() + timeout;
        long now;
        while (!stopped && until > (now = System.currentTimeMillis())) {
            try {
                wait(until - now);
            } catch (InterruptedException e) {
            }
        }
        if (!stopped) {
            fireTimeoutOccured();
        }
    }

}
