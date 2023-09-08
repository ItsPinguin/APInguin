package ping.apinguin.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
  public static void copy(String from, String to, boolean replace) {
    try {
      Path pfrom = Paths.get(from), pto = Paths.get(to);

      new File(to).mkdirs();
      Files.walk(pfrom).forEach(sourcePath -> {
        try {
          Path destinationPath = pto.resolve(pfrom.relativize(sourcePath));

          if (replace && new File(destinationPath.toUri()).exists() && new File(sourcePath.toUri()).isFile()) {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
          } else if (!new File(destinationPath.toUri()).exists() && new File(sourcePath.toUri()).isFile()) {
            Files.copy(sourcePath, destinationPath);
          } else if (new File(sourcePath.toUri()).isDirectory()) {
            new File(destinationPath.toUri()).mkdirs();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object readObjectFromProject(String path) {
    try {
      ClassLoader classLoader = FileUtils.class.getClassLoader();
      ObjectInputStream ois = new ObjectInputStream(classLoader.getResourceAsStream(path)); // Your source file path within the project
      return ois.readObject();
    } catch (IOException | ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static Object readObject(String path) {
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)); // Your source file path within the project
      return ois.readObject();
    } catch (IOException | ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void writeObject(String path, Object o) {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
      oos.writeObject(o);
      oos.flush();
      oos.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String readStringFromProject(String path) {
    try {
      ClassLoader classLoader = FileUtils.class.getClassLoader();
      //ObjectInputStream ois = new ObjectInputStream(classLoader.getResourceAsStream(path)); // Your source file path within the project
      System.out.println(new BufferedInputStream(classLoader.getResourceAsStream(path)));
      return new String(classLoader.getResourceAsStream(path).readAllBytes());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static String readString(String path) {
    try {
      FileInputStream ois = new FileInputStream(path); // Your source file path within the project
      return new String(ois.readAllBytes());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void writeString(String path, String s) {
    try {
      new File(path.replaceAll(new File(path).getName(), "")).mkdirs();
      new File(path).createNewFile();
      FileWriter fw = new FileWriter(path);
      fw.write(s);
      fw.flush();
      fw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static JSONObject readJSONObject(String path) {
    JSONParser jsonParser = new JSONParser();
    try {
      String string = new String(new FileInputStream(path).readAllBytes());
      string = string.replaceAll("&", "§");
      return (JSONObject) jsonParser.parse(string);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    return new JSONObject();
  }

  public static void writeJSONObject(String path, JSONObject jsonObject) {
    try (FileWriter file = new FileWriter(path)) {
      file.write(jsonObject.toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
