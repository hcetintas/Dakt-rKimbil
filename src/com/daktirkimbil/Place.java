package com.daktirkimbil;

public class Place {
private String Id;
private String Name;
private String Vicinity;
private Double Lat;
private Double Lng;
private String Icon;


String getId() {
	return Id;
}
void setId(String id) {
	Id = id;
}
String getName() {
	return Name;
}
void setName(String name) {
	Name = name;
}
String getVicinity() {
	return Vicinity;
}
void setVicinity(String vicinity) {
	Vicinity = vicinity;
}
Double getLat() {
	return Lat;
}
void setLat(Double lat) {
	Lat = lat;
}
Double getLng() {
	return Lng;
}
void setLng(Double lng) {
	Lng = lng;
}
String getIcon() {
	return Icon;
}
void setIcon(String icon) {
	Icon = icon;
}

}
