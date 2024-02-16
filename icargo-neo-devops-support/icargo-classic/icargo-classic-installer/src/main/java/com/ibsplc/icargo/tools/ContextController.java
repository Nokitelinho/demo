package com.ibsplc.icargo.tools;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContextController {

	@GetMapping("/")
	public String index() {
		return "dashboard";
	}

	@GetMapping("/s")
	public String s() {
		return "test";
	}

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "You Successfully Uploaded" + file.getOriginalFilename() + "!");

		return "redirect:/";
	}
}
