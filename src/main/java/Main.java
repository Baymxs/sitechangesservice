public class Main {
    public static void main(String[] args) {
        try {
            new SiteCheckingService().start(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
