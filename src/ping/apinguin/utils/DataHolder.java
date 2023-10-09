package ping.apinguin.utils;

import java.util.HashMap;

public class DataHolder {
  private HashMap<String, Object> data=new HashMap<>();
  public void set(String path, Object value){
    if (!path.contains(".")) {
      data.put(path, value);
      return;
    }
    String[] a=path.split("\\.");
    String[] b=new String[a.length-1];
    System.arraycopy(a, 0, b,0,a.length-1);
    HashMap<String, Object> current=data;
    for (String s : b) {
      current.computeIfAbsent(s, k -> new HashMap<>());
      current= ((HashMap<String, Object>) current.get(s));
    }
    current.put(a[a.length-1],value);
  }

  public Object get(String path){
    if (!path.contains("."))
      return data.get(path);
    String[] a=path.split("\\.");
    String[] b=new String[a.length-1];
    System.arraycopy(a, 0, b,0,a.length-1);
    HashMap<String, Object> current=data;
    for (String s : b) {
      current.computeIfAbsent(s, k -> new HashMap<>());
      current= ((HashMap<String, Object>) current.get(s));
    }
    return current.get(a[a.length-1]);
  }

  public Object get(String path, Object defaultObject){
    return get(path) !=null ? get(path) : defaultObject;
  }

  public HashMap<String, Object> getData() {
    return data;
  }

  public void setData(HashMap<String, Object> data) {
    this.data = data;
  }
}
