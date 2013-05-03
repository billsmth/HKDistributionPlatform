package com.hk.distribution.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hk.distribution.model.FormConfig;

@Controller
@RequestMapping("/pf")
public class ProductFormController {

	@RequestMapping("configur")
	public ModelAndView productFormConfigur(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("productform/pf-maintain");
		return mav;
	}
	
	 	@RequestMapping("/generate")
	    @ResponseBody
	    public String generateTemplate(@RequestParam("id") String[] id, @RequestParam("pt") String pt) {
	 		StringBuffer innerHTML = new StringBuffer();
	 		
	 		String templatePath = "http://localhost:8080/DistributionPlatform/template/FormDemo.html";
	 		/**start using jsoup generate html**/
	 		try{
	 			Document doc = Jsoup.parse(new URL(templatePath).openStream(),"GBK",templatePath);
	 			Elements dynamicForm =  doc.select("div#dynamicForm");
	 			innerHTML.append("<h3>填写联系信息<br/><em>请告诉我们您的联系方式。</em></h3>");
	 			for(String input :id){
	 				
	 				Map<String,String> formMap = FormConfig.getFormElement();
	 				for(Map.Entry<String, String> entry : formMap.entrySet()){
	 					if(input.equals(entry.getKey().toString())){
	 						innerHTML.append(entry.getValue());
	 					}
	 				}
	 			}
	 			dynamicForm.prepend(innerHTML.toString());
	 			String html=doc.html();
				FileUtils.writeStringToFile(new File("D://"+pt+"test.html"), html);
	 		}catch(IOException e){
	 			e.printStackTrace();
	 		}
	 		
	        return "{'success':true}";
	    }
	
}
