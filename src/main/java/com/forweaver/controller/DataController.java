package com.forweaver.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forweaver.domain.Data;
import com.forweaver.service.DataService;

@Controller
@RequestMapping("/data")
public class DataController {
	@Autowired DataService dataService;
	
	@RequestMapping(value = "/{dataID}")
	public void data(@PathVariable("dataID") String dataID, HttpServletResponse res)
			throws IOException {
		Data data = dataService.get(dataID);
		if (data == null) {
			res.sendRedirect("http://www.gravatar.com/avatar/a.jpg");
			return;
		} else {
			byte[] imgData = data.getContent();
			res.setContentType(data.getType());
			OutputStream o = res.getOutputStream();
			o.write(imgData);
			o.flush();
			o.close();
			return;
		} 

	}
}
