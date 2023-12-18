package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DAO.DAO;
import com.example.demo.entity.Flower;
import com.example.demo.entity.SoilMoisture;
import com.example.demo.entity.Threshold;
import com.example.demo.mqtt.PublishSample;
import com.example.demo.mqtt.SubscribeSample;

@Controller
public class Controll {
	private DAO dao = new DAO();
	private SubscribeSample sub = new SubscribeSample();
	private PublishSample pub = new PublishSample("0");
	private Threshold th = new Threshold();
	private String typeFlowers;
	private String key = "";
	
	public Controll() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTypeFlowers() {
		return typeFlowers;
	}

	public void setTypeFlowers(String typeFlowers) {
		this.typeFlowers = typeFlowers;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	@GetMapping("/home")
	public String getAllHumAllTime(Model model) {
		pub = new PublishSample("0");
		th = dao.getThresholdByTypeFlower(typeFlowers);
		model.addAttribute("min", th.getMin());
		model.addAttribute("max", th.getMax());
		model.addAttribute("typeF", typeFlowers);
		return "home";
	}
	
	@GetMapping("/manage")
	public String getAllFlower(Model model) {
		List<Flower> list = dao.getFlowerByKey(key);
		System.out.println(key);
		model.addAttribute("list", list); 
		return "manage";
	}
	
	@GetMapping("/index")
	public String loadImage() {
		return "index";
	}
	
	@GetMapping("/index/home")
	public String home(@RequestParam("image") String path, Model model) {
		String urlModel = "http://127.0.0.1:5000/predict-plant/" + path;
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(urlModel, HttpMethod.POST, requestEntity, String.class);
        String res = response.getBody();
        res = res.substring(1, res.length()-2);
        System.out.println(res);
        setTypeFlowers(res);
        
		return "redirect:/home";
	}
	
	@GetMapping("/form")
	public String changeToForm(Model model, @RequestParam("type") String type) {
		th = dao.getThresholdByTypeFlower(type);
		setTypeFlowers(type);
		model.addAttribute("min", th.getMin());
		model.addAttribute("max", th.getMax());
		return "form";
	}
	
	@GetMapping("/save/threshoil")
	public String saveThreshoil(@RequestParam("min") int min, @RequestParam("max") int max) {
		dao.updateThresoil(min, max, typeFlowers);
		return "redirect:/manage";
	}
	
	@GetMapping("/delete")
	public String deletePlant(@RequestParam("id") int id) {
		dao.deletePlant(id);
		return "redirect:/manage";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("name") String name) {
		setKey(name);
		return "redirect:/manage";
	}
	
	@GetMapping("/addnew")
	public String toAddNewForm() {
		return "addnew";
	}
	
	@GetMapping("/add/-1")
	public String addNewPlant(@RequestParam("type") String type, @RequestParam("min") int min, 
			@RequestParam("max") int max) {
		Flower fl = new Flower(type, min, max);
		dao.addNewPlant(fl);
		return "redirect:/manage";
	}

	@GetMapping("/auto/open")
	@ResponseBody
	public String controllAutoPump() {
		th = dao.getThresholdByTypeFlower(typeFlowers);
		int min = th.getMin();
		int max = th.getMax();
		float maxHum = sub.getSoil().getHum();
		if (maxHum < min) {
			pub = new PublishSample("1");
			return "Open pump. Watering";
		} else if (maxHum <= max && maxHum >= min) {
			pub = new PublishSample("0");
			return "Close pump. Humidity is full";
		} else {
			pub = new PublishSample("0");
			return "Close pump. Humidity is over";
		}

	}

	@GetMapping("/manual/open")
	@ResponseBody
	public String controllManualPump() {
		pub = new PublishSample("1");
		return "Open pump. Watering";
	}
	
	@GetMapping("/manual/close")
	@ResponseBody
	public String manualClosePump() {
		pub = new PublishSample("0");
		return "Close pump. Stop Watering";
	}

	@GetMapping(value = "/home/time/hum")
	@ResponseBody
	public Map<String, Object> getTime() {
		Map<String, Object> data = new HashMap<>();
		List<SoilMoisture> list = dao.getAllHumAllTime();
		List<String> times = new ArrayList<>();
		List<Float> hums = new ArrayList<>();

		for (SoilMoisture moi : list) {
			System.out.println(moi.toString());
			times.add(moi.getTime());
			hums.add(moi.getHum());
		}

		data.put("time", times);
		data.put("values", hums);
		return data;
	}

	@GetMapping("/update-gauge")
	@ResponseBody
	public float updateGauge() {
		float response = sub.getSoil().getHum();
		System.out.println(response);
		return response;
	}

}
