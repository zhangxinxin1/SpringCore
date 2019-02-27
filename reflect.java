public class t {  
    public static void main(String[] args){  
        Dto d = new Dto();  
        d.setUserName("LRR");  
        d.setPassword("123");  
        // 已知attribute name 组装 getter语句  
        String getter = "get" + "UserName";// getUserName  
        try {  
            // 通过method的反射方法获取其属性值  
            Method method = d.getClass().getMethod(getter, new Class[]{});  
            Object value = method.invoke(d, new Object[]{});  
            System.out.print(value);  
        } catch (IllegalArgumentException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
        } catch (IllegalAccessException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
        } catch (InvocationTargetException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
        } catch (SecurityException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (NoSuchMethodException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
