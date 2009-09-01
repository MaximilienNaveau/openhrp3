package com.generalrobotix.ui.grxui;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.generalrobotix.ui.GrxPluginManager;
import com.generalrobotix.ui.util.GrxCorbaUtil;
import com.generalrobotix.ui.util.GrxProcessManager;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.generalrobotix.ui.grxui.GrxUIPerspectiveFactory";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	public void preStartup(){
		//window が開く前に実行される　//
		Activator activator = Activator.getDefault();
		if(activator.getImageRegistry() == null)
			try {
				activator.registryImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if(activator.getFontRegistry() == null)
			activator.registryFont();
		if(activator.getColorRegistry() == null)
			activator.registryColor();
		activator.startGrxUI();
	}
	
	public void postShutdown(){
		//window が閉じられたのち実行される　//
		GrxProcessManager.shutDown();
        try {
            GrxCorbaUtil.getORB().shutdown(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
		Activator.getDefault().stopGrxUI();
	}
}