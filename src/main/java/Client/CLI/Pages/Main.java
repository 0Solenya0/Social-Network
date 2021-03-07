package Client.CLI.Pages;

import Client.CLI.ConsoleColors;
import Server.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(User.class);

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        logger.info("hi");
        logger.error("hi");
        System.out.println(User.getFilter().getUsername("Solenya"));
        while (true) {
            System.out.println(ConsoleColors.YELLOW + "Already have an account? (y/n)");
            String response = scanner.next();
            if (response.equals("y")) {
                login();
                break;
            }
            else if (response.equals("n"))
                register();
            else
                System.out.println(ConsoleColors.RED + "Please enter valid response.");
        }
    }

    public static void login() {
        while (true) {
            System.out.println(ConsoleColors.YELLOW + "Enter your username:");
            String username = scanner.next();
            System.out.println(ConsoleColors.YELLOW + "Enter your password:");
            String password = scanner.next();
            if (Server.Requests.login(username, password))
                System.out.println(ConsoleColors.GREEN + "Logged in successfully.");
            else
                System.out.println(ConsoleColors.RED + "Username or password is wrong.");
        }
    }

    public static void register() {

    }
}
