package com.dafy.config.controller;

import com.dafy.config.domain.Environment;
import com.dafy.config.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseController {

	@Autowired
	private EnvironmentService environmentService;

	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("menuEnvironmentList",getEnvironmentList());
		return modelAndView;
	}

	private List<Environment> getEnvironmentList(){
		return environmentService.getAllEnvironmentList();
	}
}
