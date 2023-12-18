package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Flower;
import com.example.demo.entity.SoilMoisture;
import com.example.demo.entity.Threshold;

public class DAO {
	private final static String addHum = "insert into soilmoisture (hum, time) values (?, ?)";
	private final static String selectAllHum = "SELECT * FROM soilmoisture ORDER BY id DESC LIMIT 15";
	private final static String getThreshold = "select * from flower where type = ?";
	private final static String updateThreshold = "update flower set min = ?, max = ? where type = ?";
	private final static String getAllFlower = "select * from flower";
	private final static String deletePlant = "delete from flower where id = ?";
	private final static String addNewPlant = "insert into flower (type, min, max) values (?, ?, ?)";
	private final static String searchByName = "select * from flower where type like ?";
	
	private ConnectDB conn = new ConnectDB();
	private float maxHumTmp = 0;
	
	public DAO() {
		// TODO Auto-generated constructor stub
	}
	
	public float getMaxHumTmp() {
		return maxHumTmp;
	}
	
	public List<Flower> getAllFlower(){
		List<Flower> list = new ArrayList<>();
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(getAllFlower);
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				
				Flower fl = new Flower(id, type, min, max);
				list.add(fl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void setMaxHumTmp(float maxHumTmp) {
		this.maxHumTmp = maxHumTmp;
	}

	public void addHum(SoilMoisture hum) {
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(addHum);
			pr.setFloat(1, hum.getHum());
			pr.setString(2, hum.getTime());
			
			pr.executeUpdate();
			pr.close();
			//conn.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<SoilMoisture> getAllHumAllTime(){
		List<SoilMoisture> list = new ArrayList<>();
		
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(selectAllHum);
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				float hum = rs.getFloat("hum");
				String time = rs.getString("time");
				
				SoilMoisture humsoil = new SoilMoisture(id, hum, time);
				list.add(humsoil);
			}
			this.maxHumTmp = list.get(0).getHum();
			rs.close();
			pr.close();
//			conn.getConnection().close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Threshold getThresholdByTypeFlower(String type) {
		Threshold th = new Threshold();
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(getThreshold);
			pr.setString(1, type);
			ResultSet rs = pr.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				
				th = new Threshold(id, min, max);
			}
			rs.close();
			pr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return th;
	}

	public void updateThresoil(int min, int max, String type) {
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(updateThreshold);
			pr.setInt(1, min);
			pr.setInt(2, max);
			pr.setString(3, type);	
			
			pr.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deletePlant(int id) {
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(deletePlant);
			pr.setInt(1, id);
			
			pr.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addNewPlant(Flower fl) {
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(addNewPlant);
			pr.setString(1, fl.getType());
			pr.setInt(2, fl.getMin());
			pr.setInt(3, fl.getMax());
			
			pr.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Flower> getFlowerByKey(String key){
		List<Flower> list = new ArrayList<>();
		try {
			PreparedStatement pr = conn.getConnection().prepareStatement(searchByName);
			pr.setString(1, "%" + key + "%");
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				
				Flower fl = new Flower(id, type, min, max);
				list.add(fl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
