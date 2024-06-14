package com.example.homeservice.menu;

import com.example.homeservice.enums.OrderStatus;
import com.example.homeservice.enums.SpecialistStatus;
import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.*;
import com.example.homeservice.service.*;
import jakarta.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hibernate.query.sqm.tree.SqmNode.log;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);


    AdminService adminService;
    CustomerService customerService;
    SpecialistService specialistService;
    AddressService addressService;
    MainServiceService mainServiceService;
    SubServiceService subServiceService;
    OrderService orderService;
    OfferService offerService;
    CommentService commentService;

    Validator validator;

    public Menu(AdminService adminService, CustomerService customerService, SpecialistService specialistService, AddressService addressService, MainServiceService mainServiceService, SubServiceService subServiceService, OrderService orderService, OfferService offerService, CommentService commentService, Validator validator) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.addressService = addressService;
        this.mainServiceService = mainServiceService;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
        this.offerService = offerService;
        this.commentService = commentService;
        this.validator = validator;
    }

    public int getNumberFromUser() {
        int num = 0;
        try {
            num = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.nextLine();
        }
        return num;
    }
    public String getStringFromUser() {
        String input = null;
        try {
            input = scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return input;
    }
    public String getUsername() {
        System.out.println("please enter username");
        return getStringFromUser();
    }
    public String getPassword() {
        System.out.println("please enter your password");
        System.out.println("Hint: password has to be between 8 to 10 and must contain at least 1 lower and upper case and 1 digit and 1 char");
        return getStringFromUser();
    }
    public String getFirstName() {
        System.out.println("please enter your first name");
        return getStringFromUser();
    }
    public String getLastName() {
        System.out.println("please enter your last name");
        return getStringFromUser();
    }
    public String getNationalCode() {
        System.out.println("please enter your National Code");
        return getStringFromUser();
    }
    public String getPhoneNumber() {
        System.out.println("please enter your Phone Number");
        return getStringFromUser();
    }
    public String getBirthDate() {
        System.out.println("please enter your Birth Date");
        return getStringFromUser();
    }
    public String getEmail() {
        System.out.println("please enter your email");
        return getStringFromUser();
    }
    public void startMenu() {
        System.out.println("***************** WELCOME TO HOME SERVICE APPLICATION *****************");
        System.out.println("1.SIGNUP");
        System.out.println("2.LOGIN");
        System.out.println("Please press 1 or 2 : ");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> signupMenu();
            case 2 -> loginMenu();
            default -> startMenu();
        }
    }
    public void signupMenu() {
        System.out.println("***************** WELCOME TO SIGNUP MENU *****************");
        System.out.println("1.Customer");
        System.out.println("2.Specialist");
        System.out.println("3.Exit");
        System.out.println("Please press 1 or 2 : ");
        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> signupCustomer();
            case 2 -> signupSpecialist();
            case 3 -> startMenu();
            default -> signupMenu();
        }
    }
    public void loginMenu() {
        System.out.println("***************** WELCOME TO LOGIN MENU *****************");
        System.out.println("press 1 for admin processes menu");
        System.out.println("press 2 for specialist processes menu");
        System.out.println("press 3 for customer processes menu");
        System.out.println("press 4 to exit");
        int choose = getNumberFromUser();

        switch (choose) {
            case 1 -> checkLoginAdmin();
            case 2 -> checkLoginSpecialist();
            case 3 -> checkLoginCustomer();
            case 4 -> startMenu();
            default -> loginMenu();
        }
    }
    public Admin loginAdmin() {
        String username = getUsername();
        String password = getPassword();
        return adminService.findByUsernameAndPassword(username, password);
    }
    public Specialist loginSpecialist() {
        String username = getUsername();
        String password = getPassword();
        return specialistService.findByUsernameAndPassword(username, password);
    }
    public Customer loginCustomer() {
        String username = getUsername();
        String password = getPassword();
        return customerService.findByUsernameAndPassword(username, password);
    }
    public void checkLoginAdmin() {
        Admin admin = loginAdmin();
        if (admin != null) {
            adminProcessesMenu(admin);
        } else {
            System.out.println("wrong username or password");
            loginMenu();
        }
    }
    public void checkLoginSpecialist() {
        Specialist specialist = loginSpecialist();
        if (specialist != null) {
            specialistProcessesMenu(specialist);
        } else {
            System.out.println("wrong username or password");
            loginMenu();
        }
    }
    public void checkLoginCustomer() {
        Customer customer = loginCustomer();
        if (customer != null) {
            customerProcessesMenu(customer);
        } else {
            System.out.println("wrong username or password");
            loginMenu();
        }
    }
    public void adminProcessesMenu(Admin admin) {
        System.out.println("welcome dear " + admin.getUsername());
        System.out.println("press 1 for specialist process");
        System.out.println("press 2 for main service process");
        System.out.println("press 3 for sub service process");
        System.out.println("press 4 exit");

        int choose = getNumberFromUser();

        switch (choose) {
            case 1 -> adminSpecialistProcess(admin);
            case 2 -> adminMainServiceProcess(admin);
            case 3 -> adminSubServiceProcess(admin);
            case 4 -> loginMenu();
            default -> adminProcessesMenu(admin);
        }
    }
    public void adminSpecialistProcess(Admin admin) {
        System.out.println("press 1 to approve specialist by admin");
        System.out.println("press 2 for assign a specialist for sub service by admin");
        System.out.println("press 3 for delete a specialist by admin");
        System.out.println("press 4 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> approveSpecialist(admin);
            case 2 -> assignSpecialistToSubServices(admin);
            case 3 -> deleteSpecialistByAdmin(admin);
            case 4 -> adminProcessesMenu(admin);
            default -> adminSpecialistProcess(admin);
        }
    }
    public void adminMainServiceProcess(Admin admin) {
        System.out.println("press 1 for add a main service");
        System.out.println("press 2 for update a main service");
        System.out.println("press 3 for delete a main service");
        System.out.println("press 4 exit");

        int choose = getNumberFromUser();
        switch (choose) {

            case 1 -> addMainService(admin);
            case 2 -> updateMainService();
            case 3 -> deleteMainServiceByAdmin();
            case 4 -> adminProcessesMenu(admin);
            default -> adminMainServiceProcess(admin);

        }
    }
    public void adminSubServiceProcess(Admin admin) {
        System.out.println("press 1 for add a sub service");
        System.out.println("press 2 for update a sub service");
        System.out.println("press 3 for delete a sub service");
        System.out.println("press 4 exit");

        int choose = getNumberFromUser();
        switch (choose) {

            case 1 -> addSubService(admin);
            case 2 -> updateSubService();
            case 3 -> deleteSubServiceByAdmin();
            case 4 -> adminProcessesMenu(admin);
            default -> adminMainServiceProcess(admin);
        }
    }
    public void commentMenu(Customer customer) {
        System.out.println("press 1 for show comments");
        System.out.println("press 2 for add comment by customer");
        System.out.println("press 3 for update comment by customer");
        System.out.println("press 4 for delete comment by customer");
        System.out.println("press 5 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> showComments(customer);
            case 2 -> addComment(customer);
            case 3 -> updateComment(customer);
            case 4 -> showAndDeleteComments(customer);
            case 5 -> customerProcessesMenu(customer);
            default -> commentMenu(customer);
        }
    }
    public void orderMenu(Customer customer) {
        System.out.println("press 1 for show orders for customer");
        System.out.println("press 2 for add order by customer");
        System.out.println("press 3 for update order by customer");
        System.out.println("press 4 for delete order by customer");
        System.out.println("press 5 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> displayOrdersForCustomer(customer);
            case 2 -> createOrder(customer);
            case 3 -> updateOrder(customer);
            case 4 -> deleteOrder(customer);
            case 5 -> customerProcessesMenu(customer);
            default -> orderMenu(customer);
        }
    }
    public void customerMenu(Customer customer) {
        System.out.println("press 1 for update profile");
        System.out.println("press 2 for change password");
        System.out.println("press 3 for delete account");
        System.out.println("press 4 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> updateCustomerProfile();
            case 2 -> changeCustomerPassword(customer);
            case 3 -> deleteCustomerAccount(customer);
            case 4 -> customerProcessesMenu(customer);
            default -> customerMenu(customer);
        }
    }
    public void customerProcessesMenu(Customer customer) {
        System.out.println("welcome dear " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("press 1 for customer menu for update profile");
        System.out.println("press 2 for order menu");
        System.out.println("Press 3 for comment menu");
        System.out.println("press 4 for show main services and sub services");
        System.out.println("press 6 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> customerMenu(customer);
            case 2 -> orderMenu(customer);
            case 3 -> commentMenu(customer);
            case 4 -> displayServicesAndSubServices(customer);
            case 5 -> loginMenu();
            default -> customerProcessesMenu(customer);
        }
    }
    public void offerMenu(Specialist specialist) {
        System.out.println("press 1 for show orders for specialist");
        System.out.println("press 2 for add offer by specialist");
        System.out.println("press 3 for update offer by specialist");
        System.out.println("press 4 for delete offer by specialist");
        System.out.println("press 5 exit");

        int choose = getNumberFromUser();
        switch (choose) {
            case 1 -> displayOrdersForSpecialist(specialist);
            case 2 -> createNewOffer(specialist);
            case 3 -> updateOffer(specialist);
            case 4 -> deleteOffer(specialist);
            case 5 -> specialistProcessesMenu(specialist);
            default -> offerMenu(specialist);
        }
    }
    public void specialistProcessesMenu(Specialist specialist) {
        System.out.println("welcome dear " + specialist.getFirstName() + " " + specialist.getLastName());
        System.out.println("press 1 for update profile");
        System.out.println("press 2 for change password");
        System.out.println("press 3 for show registered orders");
        System.out.println("press 4 for offer menu");
        System.out.println("press 5 for delete account");
        System.out.println("press 6 for exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> updateProfileSpecialist();
            case 2 -> changeSpecialistPassword(specialist);
            case 3 -> displayOrders(specialist);
            case 4 -> offerMenu(specialist);
            case 5 -> deleteSpecialistAccount(specialist);
            case 6 -> loginMenu();
            default -> specialistProcessesMenu(specialist);
        }
    }
    private void displayOrders(Specialist specialist) {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            System.out.println("There are no orders available.");
            specialistProcessesMenu(specialist);
        } else {
            System.out.println("orders:");
            for (Order order : orders) {
                System.out.println("ID: " + order.getId() + ", Description: " + order.getDescription() +
                        ", Scheduled Date: " + order.getCreatedAt() + ", Address: " + order.getAddress().getCity() +
                        ", Final Price: " + order.getFinalPrice() + ", Status: " + order.getStatus());
            }
        }
        specialistProcessesMenu(specialist);
    }
    public static byte[] getImageFile() {
        Scanner scanner = new Scanner(System.in);
        String filePath = null;
        byte[] image = null;

        while (true) {
            try {
                System.out.print("Please enter the image file path (e.g., src/main/resources/hosseinj.jpg): ");
                if (!scanner.hasNextLine()) {
                    System.out.println("No input received. Please try again.");
                    continue;
                }
                filePath = scanner.nextLine();

                // Validate the file path
                File file = new File(filePath);
                if (file.exists() && !file.isDirectory() && file.getName().endsWith(".jpg")) {
                    try {
                        image = Files.readAllBytes(file.toPath());
                        break; // Successfully read the file, exit the loop
                    } catch (IOException e) {
                        System.out.println("Failed to read the image file. Please try again.");
                    }
                } else {
                    System.out.println("Invalid file path or file does not exist. Please try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return image;
    }
    public void signupCustomer() {
        Scanner scanner = new Scanner(System.in);
        List<Address> addresses = new ArrayList<>();

        try {
            // Get customer information
            System.out.println("Enter the first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter the last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter the username:");
            String username = scanner.nextLine();

            System.out.println("Enter the password:");
            String password = scanner.nextLine();

            System.out.println("Enter the email:");
            String email = scanner.nextLine();

            System.out.println("Enter the phone number:");
            String phoneNumber = scanner.nextLine();

            // Get address information
            while (true) {
                System.out.println("Enter address (or type 'done' to finish):");
                String addressLine = scanner.nextLine();
                if (addressLine.equalsIgnoreCase("done")) {
                    break;
                }
                System.out.println("Enter city:");
                String city = scanner.nextLine();
                System.out.println("Enter state:");
                String state = scanner.nextLine();

                String postalCode;
                while (true) {
                    System.out.println("Enter postal code:");
                    postalCode = scanner.nextLine();

                    // Check for duplicate postal code
                    if (!Objects.equals(addressService.existsByPostalCode(postalCode), postalCode)) {
                        break;
                    }
                    System.out.println("Postal code already exists! Please enter a different postal code.");
                }

                Address address = Address.builder()
                        .city(city)
                        .state(state)
                        .addressLine(addressLine)
                        .zipCode(postalCode)
                        .build();
                addresses.add(address);
            }

            Customer customer = Customer.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .password(password)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .addresses(addresses)
                    .credit(6000000D)
                    .build();

            if (customerService.validate(customer)) {
                Customer savedCustomer = customerService.save(customer);
                for (Address address : addresses) {
                    address.setCustomer(savedCustomer); // Set the customer to the saved customer
                    addressService.save(address); // Save the address with customer ID
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                    log.info(savedCustomer.getId() + " successfully added");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startMenu();
            } else {
                signupCustomer();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void approveSpecialist(Admin admin) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Display the list of specialists whose status is NEW or PENDING_APPROVAL
            List<Specialist> newOrPendingSpecialists = specialistService.findByStatusIn(Arrays.asList(SpecialistStatus.NEW, SpecialistStatus.PENDING_APPROVAL));
            if (newOrPendingSpecialists.isEmpty()) {
                System.out.println("No new or pending approval specialists found.");
                adminSpecialistProcess(admin);
                return; // Exit the method if no new or pending approval specialists are found
            }

            System.out.println("New or Pending Approval Specialists:");
            for (Specialist specialist : newOrPendingSpecialists) {
                System.out.println("ID: " + specialist.getId() + ", Name: " + specialist.getFirstName() + " " + specialist.getLastName());
            }

            // Get specialist ID from admin for confirmation
            System.out.println("Enter the ID of the specialist to approve:");
            Long specialistId = Long.parseLong(scanner.nextLine());
            Optional<Specialist> optionalSpecialist = specialistService.findById(specialistId);
            if (optionalSpecialist.isEmpty()) {
                System.out.println("Specialist not found!");
                adminSpecialistProcess(admin);
                return; // Exit the method if the specialist is not found
            }

            Specialist savedSpecialist = optionalSpecialist.get();

            // Change specialist status
            System.out.println("Select new status:");
            System.out.println("1. PENDING_APPROVAL");
            System.out.println("2. APPROVED");
            int statusChoice = Integer.parseInt(scanner.nextLine());

            SpecialistStatus newStatus;
            switch (statusChoice) {
                case 1:
                    newStatus = SpecialistStatus.PENDING_APPROVAL;
                    break;
                case 2:
                    newStatus = SpecialistStatus.APPROVED;
                    break;
                default:
                    System.out.println("Invalid choice! Status not changed.");
                    adminSpecialistProcess(admin);
                    return; // Exit the method if the choice is invalid
            }

            // Update specialist status
            savedSpecialist.setStatus(newStatus);
            Specialist updatedSpecialist = specialistService.update(savedSpecialist, savedSpecialist.getId(), savedSpecialist.getSubServices());
            System.out.println("Specialist status successfully updated to " + updatedSpecialist.getStatus());

            // Inform admin that specialist must be approved before assigning to sub-services
            if (newStatus != SpecialistStatus.APPROVED) {
                System.out.println("Specialist must be APPROVED to be assigned to a sub-service.");
            }

            adminSpecialistProcess(admin);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Specialist not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void assignSpecialistToSubServices(Admin admin) {
        try {
            // Display the list of specialists whose status is APPROVED
            List<Specialist> approvedSpecialists = specialistService.findByStatus(SpecialistStatus.APPROVED);
            if (approvedSpecialists.isEmpty()) {
                System.out.println("No approved specialists found.");
                adminSpecialistProcess(admin);
                return; // Exit the method if no approved specialists are found
            }

            System.out.println("Approved Specialists:");
            for (Specialist specialist : approvedSpecialists) {
                System.out.println("ID: " + specialist.getId() + ", Name: " + specialist.getFirstName() + " " + specialist.getLastName());
            }

            // Get specialist ID from admin for assignment
            System.out.println("Enter the ID of the specialist to assign to sub-services:");
            Long specialistId = Long.parseLong(scanner.nextLine());
            Optional<Specialist> optionalSpecialist = specialistService.findById(specialistId);
            if (optionalSpecialist.isEmpty()) {
                System.out.println("Specialist not found!");
                adminSpecialistProcess(admin);
                return; // Exit the method if the specialist is not found
            }

            Specialist savedSpecialist = optionalSpecialist.get();

            // Display the list of subservices to assign to the specialist
            List<SubService> subServices = subServiceService.findAll();
            if (subServices.isEmpty()) {
                System.out.println("No sub-services available.");
                adminSpecialistProcess(admin);
                return; // Exit the method if no sub-services are available
            }

            System.out.println("Available Sub-Services:");
            for (SubService subService : subServices) {
                System.out.println("ID: " + subService.getId() + ", Name: " + subService.getSubserviceName());
            }

            // Get subservice ID from admin to assign to specialist
            System.out.println("Enter the IDs of the sub-services to assign to the specialist (comma separated):");
            String[] subServiceIds = scanner.nextLine().split(",");
            Set<SubService> subServiceSet = new HashSet<>();

            for (String subServiceId : subServiceIds) {
                Optional<SubService> optionalSubService = subServiceService.findById(Long.parseLong(subServiceId.trim()));
                if (optionalSubService.isPresent()) {
                    subServiceSet.add(optionalSubService.get());
                } else {
                    System.out.println("Sub-service ID " + subServiceId + " not found!");
                    adminSpecialistProcess(admin);
                    return; // Exit the method if any sub-service is not found
                }
            }

            // Add sub-services to specialist and update
            savedSpecialist.setSubServices(subServiceSet);
            Specialist updatedSpecialist = specialistService.update(savedSpecialist, savedSpecialist.getId(), subServiceSet);
            System.out.println("Specialist " + updatedSpecialist.getFirstName() + " " + updatedSpecialist.getLastName() + " has been assigned to the selected sub-services.");
            adminSpecialistProcess(admin);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Specialist or Sub-service not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        adminSpecialistProcess(admin);
    }
    // String password = PasswordGenerator.generateRandomPassword();
    // String profilePicture = getFileNameFromPath("src/main/resources/hosseinj.jpg");
    public void signupSpecialist() {
        Set<SubService> subServices = new HashSet<>();
        try {
            // Get specialist information
            System.out.println("Enter the first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter the last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter the username:");
            String username = scanner.nextLine();

            System.out.println("Enter the password:");
            String password = scanner.nextLine();

            System.out.println("Enter the email:");
            String email = scanner.nextLine();

            System.out.println("Enter the phone number:");
            String phoneNumber = scanner.nextLine();

            byte[] image = getImageFile();
            if (image != null) {
                System.out.println("Image file read successfully. Length: " + image.length + " bytes.");
            } else {
                System.out.println("Failed to read the image file.");
            }

            System.out.println("Enter years of experience:");
            int yearsOfExperience = Integer.parseInt(scanner.nextLine());


            // Select the initial status
            SpecialistStatus status = SpecialistStatus.NEW;

            Specialist specialist = Specialist.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .password(password)
                    .subServices(subServices)
                    .yearsOfExperience(yearsOfExperience)
                    .status(status)
                    .email(email)
                    .profilePicture(image)
                    .phoneNumber(phoneNumber)
                    .credit(70000000)
                    .build();

            if (specialistService.validate(specialist)) {
                Specialist savedSpecialist = specialistService.save(specialist);
                try {
                    TimeUnit.SECONDS.sleep(5);
                    log.info(savedSpecialist.getId() + " successfully added");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startMenu();
            } else {
                signupSpecialist();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void addMainService(Admin admin) {
        try {
            // Get the main service name
            System.out.println("Enter the main service name:");
            String mainServiceName = scanner.nextLine();

            // Create and save the subservice
            MainService mainService = MainService.builder()
                    .serviceName(mainServiceName)
                    .subServices(new HashSet<>())
                    .build();

            // Save the main service
            MainService savedService = mainServiceService.save(mainService);
            log.info(savedService.getId() + " successfully added");
            adminMainServiceProcess(admin);

        } catch (InformationDuplicateException e) {
            log.error("Duplicate information: " + e.getMessage());
            adminMainServiceProcess(admin);
        } catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void addSubService(Admin admin) {

        try {
            // Display the main service list
            List<MainService> mainServices = mainServiceService.findAll();
            if (mainServices.isEmpty()) {
                System.out.println(("No main services available!"));
                adminSubServiceProcess(admin);
                return;
            }

            System.out.println("Available main services:");
            for (MainService mainService : mainServices) {
                System.out.println(mainService.getId() + ": " + mainService.getServiceName());
            }

            // Select the main service
            System.out.println("Enter the main service ID from the list above:");
            Long mainServiceId = Long.parseLong(scanner.nextLine());
            Optional<MainService> optionalMainService = mainServiceService.findById(mainServiceId);
            if (optionalMainService.isEmpty()) {
                System.out.println("Main service not found!");
                adminSubServiceProcess(admin);
                return;
            }
            MainService mainService = optionalMainService.get();
            // Get subservice details
            System.out.println("Enter the sub-service name:");
            String subServiceName = scanner.nextLine();

            System.out.println("Enter the sub-service price:");
            double subServicePrice = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter the sub-service description:");
            String subServiceDescription = scanner.nextLine();

            // Create and save the subservice
            SubService subService = SubService.builder()
                    .subserviceName(subServiceName)
                    .price(subServicePrice)
                    .description(subServiceDescription)
                    .service(mainService)
                    .build();

            SubService savedSubService = subServiceService.save(subService);
            System.out.println(savedSubService.getId() + " successfully added");
            adminSubServiceProcess(admin);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (InformationDuplicateException e) {
            System.out.println("Duplicate information: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void updateCustomerProfile() {
        try {
            // Get the customer ID
            System.out.println("Enter the ID of the customer to update:");
            Long customerId = Long.parseLong(scanner.nextLine());

            Optional<Customer> optionalCustomer = customerService.findById(customerId);
            if (optionalCustomer.isEmpty()) {
                System.out.println("Customer not found!");
                return;
            }
            Customer customer = optionalCustomer.get();

            // Get new information to update profile
            System.out.println("Enter the new first name:");
            String newFirstName = scanner.nextLine();
            System.out.println("Enter the new last name:");
            String newLastName = scanner.nextLine();
            System.out.println("Enter the new username:");
            String newUsername = scanner.nextLine();
            System.out.println("Enter the new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Enter the new email:");
            String newEmail = scanner.nextLine();
            System.out.println("Enter the new phone number:");
            String newPhoneNumber = scanner.nextLine();

            // Update the customer profile details
            customer.setFirstName(newFirstName);
            customer.setLastName(newLastName);
            customer.setUsername(newUsername);
            customer.setPassword(newPassword);
            customer.setEmail(newEmail);
            customer.setPhoneNumber(newPhoneNumber);

            // Update the customer addresses
            List<Address> addresses = customer.getAddresses();
            while (true) {
                System.out.println("Choose an option for addresses:");
                System.out.println("1. Add new address");
                System.out.println("2. Edit existing address");
                System.out.println("3. Remove existing address");
                System.out.println("4. Finish updating addresses");
                int addressChoice = Integer.parseInt(scanner.nextLine());

                if (addressChoice == 4) {
                    break;
                }

                switch (addressChoice) {
                    case 1:
                        // Add new address
                        System.out.println("Enter address line:");
                        String addressLine = scanner.nextLine();
                        System.out.println("Enter city:");
                        String city = scanner.nextLine();
                        System.out.println("Enter state:");
                        String state = scanner.nextLine();
                        System.out.println("Enter postal code:");
                        String postalCode = scanner.nextLine();


                        Address newAddress =  Address.builder()
                                .addressLine(addressLine)
                                .city(city)
                                .state(state)
                                .zipCode(postalCode)
                                .build();
                        addresses.add(newAddress);
                        break;

                    case 2:
                        // Edit existing address
                        System.out.println("Enter the index of the address to edit (starting from 0):");
                        int editIndex = Integer.parseInt(scanner.nextLine());
                        if (editIndex < 0 || editIndex >= addresses.size()) {
                            System.out.println("Invalid index!");
                            break;
                        }
                        Address addressToEdit = addresses.get(editIndex);

                        System.out.println("Enter new address line (or leave empty to keep current):");
                        addressLine = scanner.nextLine();
                        if (!addressLine.isEmpty()) {
                            addressToEdit.setAddressLine(addressLine);
                        }
                        System.out.println("Enter new city (or leave empty to keep current):");
                        city = scanner.nextLine();
                        if (!city.isEmpty()) {
                            addressToEdit.setCity(city);
                        }
                        System.out.println("Enter new state (or leave empty to keep current):");
                        state = scanner.nextLine();
                        if (!state.isEmpty()) {
                            addressToEdit.setState(state);
                        }
                        System.out.println("Enter new postal code (or leave empty to keep current):");
                        postalCode = scanner.nextLine();
                        if (!postalCode.isEmpty()) {
                            addressToEdit.setZipCode(postalCode);
                        }
                    case 3:
                        // Remove existing address
                        System.out.println("Enter the index of the address to remove (starting from 0):");
                        int removeIndex = Integer.parseInt(scanner.nextLine());
                        if (removeIndex < 0 || removeIndex >= addresses.size()) {
                            System.out.println("Invalid index!");
                            break;
                        }
                        addresses.remove(removeIndex);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            if (customerService.validate(customer)) {
                Customer updatedCustomer = customerService.update(customer, customerId);
                try {
                    TimeUnit.SECONDS.sleep(5);  // 5 second delay to simulate processing
                    System.out.println(updatedCustomer.getId() + " successfully updated");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startMenu();
            } else {
                System.out.println("Validation failed. Please try again.");
                updateCustomerProfile();
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Customer not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void changeCustomerPassword(Customer customer) {
        try {
            // Get the new password
            System.out.println("Enter your new password:");
            String newPassword = scanner.nextLine();

            if (newPassword.isBlank()) {
                System.out.println("Password must not be blank");
                return;
            }

            // Update the password
            customer.setPassword(newPassword);
            customerService.update(customer, customer.getId());

            System.out.println("Password successfully updated!");
            log.info("Password successfully updated for customer with username: " + customer.getUsername());
        } catch (Exception e) {
            log.error("An error occurred while updating the password: " + e.getMessage());
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void updateProfileSpecialist() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Get specialist ID
            System.out.println("Enter the ID of the specialist to update:");
            Long specialistId = Long.parseLong(scanner.nextLine());

           Optional<Specialist> optionalSpecialist = specialistService.findById(specialistId);
            if (optionalSpecialist.isEmpty()) {
                System.out.println("Specialist not found!");
                return;
            }
            Specialist specialist = optionalSpecialist.get();

            // Get new information to update profile
            System.out.println("Enter the new username:");
            String newUsername = scanner.nextLine();
            System.out.println("Enter the new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Enter the new years of experience:");
            int newYearsOfExperience = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the new email:");
            String newEmail = scanner.nextLine();

            // Update specialist
            specialist.setUsername(newUsername);
            specialist.setPassword(newPassword);
            specialist.setYearsOfExperience(newYearsOfExperience);
            specialist.setEmail(newEmail);

            Specialist updatedSpecialist = specialistService.update(specialist, specialistId, specialist.getSubServices());
            System.out.println(updatedSpecialist + " Specialist successfully updated");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Specialist not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void changeSpecialistPassword(Specialist specialist) {
        try {
            // Get the new password
            System.out.println("Enter your new password:");
            String newPassword = scanner.nextLine();

            if (newPassword.isBlank()) {
                System.out.println("Password must not be blank");
                return;
            }

            // Update the password
            specialist.setPassword(newPassword);
            specialistService.update(specialist, specialist.getId(), specialist.getSubServices());

            System.out.println("Password successfully updated!");
            log.info("Password successfully updated for specialist with username: " + specialist.getUsername());
        } catch (Exception e) {
            log.error("An error occurred while updating the password: " + e.getMessage());
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void updateMainService() {
        try {
            // Display the main service list
            List<MainService> mainServices = mainServiceService.findAll();
            System.out.println("Available Main Services:");
            for (MainService mainService : mainServices) {
                System.out.println(mainService.getId() + ": " + mainService.getServiceName());
            }

            // Get the main service ID to update
            System.out.println("Enter the ID of the main service to update:");
            Long serviceId = Long.parseLong(scanner.nextLine());

            // Get new information to update
            System.out.println("Enter the new name for the main service:");
            String newName = scanner.nextLine();

            // Update main service
          Optional<MainService> optionalMainService = mainServiceService.findById(serviceId);
            if (optionalMainService.isPresent()) {
                MainService service = optionalMainService.get();
                service.setServiceName(newName);

                MainService updatedService = mainServiceService.update(service, serviceId);
                System.out.println(updatedService + " Service successfully updated");
            } else {
                System.out.println("Service not found!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Service not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void updateSubService() {
        try {
            // Display the main service list
            List<MainService> mainServices = mainServiceService.findAll();
            System.out.println("Available Main Services:");
            for (MainService mainService : mainServices) {
                System.out.println(mainService.getId() + ": " + mainService.getServiceName());
            }

            // Get the main service ID
            System.out.println("Enter the ID of the main service:");
            Long mainServiceId = Long.parseLong(scanner.nextLine());

            Optional<MainService> optionalMainService = mainServiceService.findById(mainServiceId);
            if (optionalMainService.isEmpty()) {
                System.out.println("Main Service not found!");
                return;
            }
            MainService mainService = optionalMainService.get();

            // Display the list of subservices related to the main service
            List<SubService> subServices = subServiceService.findByMainService(mainService);
            System.out.println("Available Sub Services:");
            for (SubService subService : subServices) {
                System.out.println(subService.getId() + ": " + subService.getSubserviceName());
            }

            // Get the ID of the subservice to update
            System.out.println("Enter the ID of the sub-service to update:");
            Long subServiceId = Long.parseLong(scanner.nextLine());

           Optional<SubService> optionalSubService = subServiceService.findById(subServiceId);
            if (optionalSubService.isEmpty()) {
                System.out.println("Sub-service not found!");
                return;
            }
            SubService subService = optionalSubService.get();

            // Get new information to update
            System.out.println("Enter the new name for the sub-service:");
            String newName = scanner.nextLine();
            System.out.println("Enter the new price for the sub-service:");
            double newPrice = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter the new description for the sub-service:");
            String newDescription = scanner.nextLine();

            // Update the subservice
            subService.setSubserviceName(newName);
            subService.setPrice(newPrice);
            subService.setDescription(newDescription);

            SubService updatedSubService = subServiceService.update(subService, subServiceId, subService.getSpecialists());
            System.out.println(updatedSubService + " SubService successfully updated");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Sub-service not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteCustomerAccount(Customer customer) {
        try {
            // Confirm deletion
            System.out.println("Are you sure you want to delete your account? This action cannot be undone. (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                customerService.removeByUsername(customer.getUsername());
                log.info("Customer with username : " + customer.getUsername() + " successfully deleted");
                System.out.println("Your account has been successfully deleted.");
            } else {
                System.out.println("Account deletion cancelled.");
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteSpecialistAccount(Specialist specialist) {
        try {
            // Confirm deletion
            System.out.println("Are you sure you want to delete your account? This action cannot be undone. (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                specialistService.removeByUsername(specialist.getUsername());
                log.info("Specialist with username : " + specialist.getUsername() + " successfully deleted");
                System.out.println("Your account has been successfully deleted.");
            } else {
                System.out.println("Account deletion cancelled.");
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteSpecialistByAdmin(Admin admin) {
        try {
            // Get list of all specialists
            List<Specialist> allSpecialists = specialistService.findAll();
            if (allSpecialists.isEmpty()) {
                System.out.println("No specialists found.");
                adminSpecialistProcess(admin);
            }

            // Display list of specialists
            System.out.println("List of Specialists:");
            for (Specialist specialist : allSpecialists) {
                System.out.println("ID: " + specialist.getId() + ", Name: " + specialist.getFirstName() + " " + specialist.getLastName());
            }

            // Get specialist ID from admin to delete
            System.out.println("Enter the ID of the specialist to delete:");
            Long specialistId = Long.parseLong(scanner.nextLine());

            // Find specialist by ID
           Optional<Specialist> optionalSpecialist = specialistService.findById(specialistId);
            if (optionalSpecialist.isEmpty()) {
                System.out.println("Specialist not found!");
                return;
            }

            // Delete specialist
            specialistService.removeById(specialistId);
            System.out.println("Specialist with ID " + specialistId + " successfully deleted");
            log.info("Specialist with ID " + specialistId + " successfully deleted");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Specialist not found: " + e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteMainServiceByAdmin() {

        try {
            // Get list of all main services
            List<MainService> allMainServices = mainServiceService.findAll();
            if (allMainServices.isEmpty()) {
                System.out.println("No main services found.");
                return;
            }

            // Display list of main services
            System.out.println("List of Main Services:");
            for (MainService mainService : allMainServices) {
                System.out.println("ID: " + mainService.getId() + ", Name: " + mainService.getServiceName());
            }

            // Get main service ID from admin to delete
            System.out.println("Enter the ID of the main service to delete:");
            Long mainServiceId = Long.parseLong(scanner.nextLine());

            // Find main service by ID
           Optional<MainService> optionalMainService = mainServiceService.findById(mainServiceId);
            if (optionalMainService.isEmpty()) {
                System.out.println("Main service not found!");
                return;
            }

            // Delete main service
            mainServiceService.removeById(mainServiceId);
            System.out.println("Main service with ID " + mainServiceId + " successfully deleted");
            log.info("Main service with ID " + mainServiceId + " successfully deleted");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Main service not found: " + e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteSubServiceByAdmin() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Get list of all sub-services
            List<SubService> allSubServices = subServiceService.findAll();
            if (allSubServices.isEmpty()) {
                System.out.println("No sub-services found.");
                return;
            }

            // Display list of sub-services
            System.out.println("List of Sub-Services:");
            for (SubService subService : allSubServices) {
                System.out.println("ID: " + subService.getId() + ", Name: " + subService.getSubserviceName());
            }

            // Get sub-service ID from admin to delete
            System.out.println("Enter the ID of the sub-service to delete:");
            Long subServiceId = Long.parseLong(scanner.nextLine());

            // Find sub-service by ID
            Optional<SubService> optionalSubService = subServiceService.findById(subServiceId);
            if (optionalSubService.isEmpty()) {
                System.out.println("Sub-service not found!");
                return;
            }

            // Delete sub-service
            subServiceService.removeById(subServiceId);
            System.out.println("Sub-service with ID " + subServiceId + " successfully deleted");
            log.info("Sub-service with ID " + subServiceId + " successfully deleted");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Sub-service not found: " + e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void displayOrdersForCustomer(Customer customer) {
        try {
            List<Order> orders = orderService.findByCustomer(customer);
            if (orders.isEmpty()) {
                System.out.println("There are no orders available for this customer.");
                orderMenu(customer);
            } else {
                System.out.println("Orders:");
                for (Order order : orders) {
                    System.out.println("ID: " + order.getId() + ", Description: " + order.getDescription() +
                            ", Scheduled Date: " + order.getCreatedAt() + ", Address: " + order.getAddress().getCity() +
                            ", Final Price: " + order.getFinalPrice() + ", Status: " + order.getStatus());
                }
                orderMenu(customer);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching orders: " + e.getMessage());
            orderMenu(customer);
        }
    }
    public void displayOrdersForSpecialist(Specialist specialist) {
        try {
            List<Order> orders = orderService.findBySelectedSpecialist(specialist);
            if (orders.isEmpty()) {
                System.out.println("There are no orders available for this specialist.");
                offerMenu(specialist);
            } else {
                System.out.println("Orders:");
                for (Order order : orders) {
                    System.out.println("ID: " + order.getId() + ", Description: " + order.getDescription() +
                            ", Scheduled Date: " + order.getCreatedAt() + ", Address: " + order.getAddress().getCity() +
                            ", Final Price: " + order.getFinalPrice() + ", Status: " + order.getStatus());
                }
                offerMenu(specialist);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching orders: " + e.getMessage());
            offerMenu(specialist);
        }
    }
    public void displayServicesAndSubServices(Customer customer) {
        System.out.println("Available Services and Sub-Services:");
        List<MainService> services = mainServiceService.findAll();
        if (services.isEmpty()) {
            System.out.println("No services available!");
            customerProcessesMenu(customer);
            return;
        }

        for (MainService mainService : services) {
            System.out.println("Service: " + mainService.getServiceName());
            List<SubService> subServices = subServiceService.findByMainService(mainService);
            if (subServices.isEmpty()) {
                System.out.println("  No sub-services available for this service!");
                customerProcessesMenu(customer);
            } else {
                for (SubService subService : subServices) {
                    System.out.println("  Sub-Service: " + subService.getSubserviceName() + " - " + subService.getPrice());
                }
            }
        }
        customerProcessesMenu(customer);
    }
    public void createOrder(Customer customer) {
        try {

            System.out.println("Available Services and Sub-Services:");
            List<MainService> services = mainServiceService.findAll();
            if (services.isEmpty()) {
                System.out.println("No services available!");
                return;
            }

            for (MainService mainService : services) {
                System.out.println("Service: " + mainService.getServiceName());
                List<SubService> subServices = subServiceService.findByMainService(mainService);
                if (subServices.isEmpty()) {
                    System.out.println("  No sub-services available for this service!");
                } else {
                    for (SubService subService : subServices) {
                        System.out.println("  Sub-Service: " + subService.getSubserviceName() + " - " + subService.getPrice());
                    }
                }
            }

            // Get the selected service
            System.out.println("Enter the name of the service you want to select:");
            String serviceName = scanner.nextLine();
            Optional<MainService> optionalMainService = mainServiceService.findByServiceName(serviceName);
            if (optionalMainService.isEmpty()) {
                System.out.println("Service not found!");
                orderMenu(customer);
                return;
            }

            MainService mainService = optionalMainService.get();

            // Get the selected sub-service
            System.out.println("Enter the name of the sub-service you want to select:");
            String subServiceName = scanner.nextLine();
           Optional<SubService> optionalSubService = subServiceService.findBySubserviceNameAndService(subServiceName, mainService);
            if (optionalSubService.isEmpty()) {
                System.out.println("Sub-service not found!");
                orderMenu(customer);
                return;
            }
            SubService subService = optionalSubService.get();

            // Display specialists for the selected sub-service
            List<Specialist> specialists = specialistService.findBySubService(subService);
            if (specialists.isEmpty()) {
                System.out.println("No specialists found for the selected sub-service!");
                orderMenu(customer);
                return;
            }

            System.out.println("Available specialists for the selected sub-service:");
            for (int i = 0; i < specialists.size(); i++) {
                Specialist specialist = specialists.get(i);
                System.out.println(i + 1 + ". " + specialist.getFirstName() + " " + specialist.getLastName());
            }

            System.out.println("Enter the number of the specialist to use:");
            int specialistChoice = Integer.parseInt(scanner.nextLine());
            if (specialistChoice < 1 || specialistChoice > specialists.size()) {
                System.out.println("Invalid specialist choice!");
                orderMenu(customer);
                return;
            }
            Specialist selectedSpecialist = specialists.get(specialistChoice - 1);

            // Get Address details
            List<Address> customerAddresses = addressService.findByCustomer(customer);
            if (customerAddresses.isEmpty()) {
                System.out.println("No addresses found for this customer!");
                orderMenu(customer);
                return;
            }

            System.out.println("Select an address from the following list:");
            for (int i = 0; i < customerAddresses.size(); i++) {
                Address address = customerAddresses.get(i);
                System.out.println(i + 1 + ". " + address.getCity() + ", " + address.getState() + ", " + address.getAddressLine() + ", " + address.getZipCode());
            }

            System.out.println("Enter the number of the address to use:");
            int addressChoice = Integer.parseInt(scanner.nextLine());
            if (addressChoice < 1 || addressChoice > customerAddresses.size()) {
                System.out.println("Invalid address choice!");
                orderMenu(customer);
                return;
            }
            Address address = customerAddresses.get(addressChoice - 1);

            // Get Order details
            System.out.print("Enter order description: ");
            String description = scanner.nextLine();
            if (description.isBlank()) {
                throw new IllegalArgumentException("Description must not be blank");
            }

            LocalDateTime createdAt = LocalDateTime.now();

            System.out.print("Enter final price: ");
            double finalPrice = Double.parseDouble(scanner.nextLine());
            if (finalPrice < 0.0) {
                throw new IllegalArgumentException("Final price must be at least 0.0");
            }

            // Create Order
            Order order = Order.builder()
                    .description(description)
                    .createdAt(createdAt)
                    .finalPrice(finalPrice)
                    .status(OrderStatus.WAITING_FOR_SPECIALIST_OFFER)
                    .subService(subService)
                    .customer(customer)
                    .selectedSpecialist(selectedSpecialist) // Assign the selected specialist
                    .address(address)
                    .build();

            orderService.save(order);
            System.out.println("Order created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        orderMenu(customer);
    }
    public void updateOrder(Customer customer) {
        try {
            // Display all orders for the customer
            List<Order> customerOrders = orderService.findByCustomer(customer);
            if (customerOrders.isEmpty()) {
                System.out.println("No orders found for this customer!");
                orderMenu(customer);
                return;
            }

            System.out.println("Select an order from the following list:");
            for (int i = 0; i < customerOrders.size(); i++) {
                Order order = customerOrders.get(i);
                System.out.println(i + 1 + ". Order ID: " + order.getId() + ", Description: " + order.getDescription());
            }

            System.out.println("Enter the number of the order you want to update:");
            int orderChoice = Integer.parseInt(scanner.nextLine());
            if (orderChoice < 1 || orderChoice > customerOrders.size()) {
                System.out.println("Invalid order choice!");
                orderMenu(customer);
                return;
            }
            Order order = customerOrders.get(orderChoice - 1);

            // Display all services and sub-services
            System.out.println("Available Services and Sub-Services:");
            List<MainService> services = mainServiceService.findAll();
            if (services.isEmpty()) {
                System.out.println("No services available!");
                return;
            }

            for (MainService mainService : services) {
                System.out.println("Service: " + mainService.getServiceName());
                List<SubService> subServices = subServiceService.findByMainService(mainService);
                if (subServices.isEmpty()) {
                    System.out.println("  No sub-services available for this service!");
                } else {
                    for (SubService subService : subServices) {
                        System.out.println("  Sub-Service: " + subService.getSubserviceName() + " - " + subService.getPrice());
                    }
                }
            }
            // Get the selected service
            System.out.println("Enter the name of the service you want to select:");
            String serviceName = scanner.nextLine();
           Optional<MainService> optionalMainService = mainServiceService.findByServiceName(serviceName);
            if (optionalMainService.isEmpty()) {
                System.out.println("Service not found!");
                orderMenu(customer);
                return;
            }

            MainService mainService = optionalMainService.get();

            // Get the selected sub-service
            System.out.println("Enter the name of the sub-service you want to select:");
            String subServiceName = scanner.nextLine();
            Optional<SubService> optionalSubService = subServiceService.findBySubserviceNameAndService(subServiceName, mainService);
            if (optionalSubService.isEmpty()) {
                System.out.println("Sub-service not found!");
                orderMenu(customer);
                return;
            }
            SubService subService = optionalSubService.get();

            // Display specialists for the selected sub-service
            List<Specialist> specialists = specialistService.findBySubService(subService);
            if (specialists.isEmpty()) {
                System.out.println("No specialists found for the selected sub-service!");
                orderMenu(customer);
                return;
            }

            System.out.println("Available specialists for the selected sub-service:");
            for (int i = 0; i < specialists.size(); i++) {
                Specialist specialist = specialists.get(i);
                System.out.println(i + 1 + ". " + specialist.getFirstName() + " " + specialist.getLastName());
            }

            System.out.println("Enter the number of the specialist to use:");
            int specialistChoice = Integer.parseInt(scanner.nextLine());
            if (specialistChoice < 1 || specialistChoice > specialists.size()) {
                System.out.println("Invalid specialist choice!");
                orderMenu(customer);
                return;
            }
            Specialist selectedSpecialist = specialists.get(specialistChoice - 1);

            // Get Address details
            List<Address> customerAddresses = addressService.findByCustomer(customer);
            if (customerAddresses.isEmpty()) {
                System.out.println("No addresses found for this customer!");
                orderMenu(customer);
                return;
            }

            System.out.println("Select an address from the following list:");
            for (int i = 0; i < customerAddresses.size(); i++) {
                Address address = customerAddresses.get(i);
                System.out.println(i + 1 + ". " + address.getCity() + ", " + address.getState() + ", " + address.getAddressLine() + ", " + address.getZipCode());
            }

            System.out.println("Enter the number of the address to use:");
            int addressChoice = Integer.parseInt(scanner.nextLine());
            if (addressChoice < 1 || addressChoice > customerAddresses.size()) {
                System.out.println("Invalid address choice!");
                orderMenu(customer);
                return;
            }
            Address address = customerAddresses.get(addressChoice - 1);

            // Get Order details
            System.out.print("Enter order description: ");
            String description = scanner.nextLine();
            if (description.isBlank()) {
                throw new IllegalArgumentException("Description must not be blank");
            }

            LocalDateTime createdAt = LocalDateTime.now();

            System.out.print("Enter final price: ");
            double finalPrice = Double.parseDouble(scanner.nextLine());
            if (finalPrice < 0.0) {
                throw new IllegalArgumentException("Final price must be at least 0.0");
            }

            // Update Order
            order.setDescription(description);
            order.setCreatedAt(createdAt);
            order.setFinalPrice(finalPrice);
            order.setSubService(subService);
            order.setAddress(address);
            order.setSelectedSpecialist(selectedSpecialist); // Set the selected specialist

            if (orderService.validate(order)) {
                orderService.update(order, order.getId());
                System.out.println("Order successfully updated!");
            } else {
                System.out.println("Validation failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        orderMenu(customer);
    }
    public void deleteOrder(Customer customer) {
        try {
            // Display orders of the customer
            List<Order> customerOrders = orderService.findByCustomer(customer);
            if (customerOrders.isEmpty()) {
                System.out.println("No orders found for this customer!");
                orderMenu(customer);
            }

            System.out.println("Customer Orders:");
            for (int i = 0; i < customerOrders.size(); i++) {
                Order order = customerOrders.get(i);
                System.out.println(i + 1 + ". Order ID: " + order.getId() + ", Description: " + order.getDescription() + ", Created At: " + order.getCreatedAt() + ", Final Price: " + order.getFinalPrice());
            }

            // Get order ID from the customer
            System.out.println("Enter the ID of the order you want to delete:");
            Long orderId = Long.parseLong(scanner.nextLine());
            Optional<Order> order = orderService.findById(orderId);
            if (order.isEmpty()) {
                System.out.println("Order not found!");
                orderMenu(customer);
            }

            // Confirm deletion
            System.out.println("Are you sure you want to delete this order? (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                orderService.removeById(orderId);
                System.out.println("Order successfully deleted!");
                orderMenu(customer);
            } else {
                System.out.println("Deletion cancelled.");
                orderMenu(customer);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void showComments(Customer customer) {
        try {
            // Get the comments of the customer
            List<Comment> customerComments = commentService.findAllByCustomer(customer);
            if (customerComments.isEmpty()) {
                System.out.println("No comments found for this customer!");
                commentMenu(customer);
            }

            // Display the comments
            System.out.println("Your Comments:");
            for (int i = 0; i < customerComments.size(); i++) {
                Comment comment = customerComments.get(i);
                Order order = comment.getOrder();
                System.out.println(i + 1 + ". " + "Order ID: " + order.getId() + ", Sub-Service: " + order.getSubService().getSubserviceName() + ", Specialist: " + order.getSelectedSpecialist().getLastName() + ", Rating: " + comment.getRating() + ", Comment: " + comment.getComment());
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void addComment(Customer customer) {
        try {
            // Get all orders for the customer
            List<Order> customerOrders = orderService.findByCustomer(customer);
            if (customerOrders.isEmpty()) {
                System.out.println("No orders found for this customer!");
                commentMenu(customer);
                return;
            }

            // Display all orders
            System.out.println("Orders:");
            for (int i = 0; i < customerOrders.size(); i++) {
                Order order = customerOrders.get(i);
                System.out.println(i + 1 + ". Order ID: " + order.getId() + ", Sub-Service: " + order.getSubService().getSubserviceName() + ", Specialist: " + order.getSelectedSpecialist().getLastName());
            }

            // Get the selected order for commenting
            System.out.println("Enter the number of the order you want to comment on:");
            int orderChoice = Integer.parseInt(scanner.nextLine());
            if (orderChoice < 1 || orderChoice > customerOrders.size()) {
                System.out.println("Invalid order choice!");
                commentMenu(customer);
                return;
            }
            Order selectedOrder = customerOrders.get(orderChoice - 1);

            // Check if the order is in DONE or PAID status
            if (!(selectedOrder.getStatus() == OrderStatus.DONE || selectedOrder.getStatus() == OrderStatus.PAID)) {
                System.out.println("Only completed orders can be commented on!");
                commentMenu(customer);
                return;
            }

            // Get the review and rating from the customer
            System.out.print("Enter your review for the specialist: ");
            String comment = scanner.nextLine();

            System.out.print("Enter a rating for the specialist (1 to 5): ");
            int rating = Integer.parseInt(scanner.nextLine());
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating! It must be between 1 and 5.");
                commentMenu(customer);
                return;
            }

            // Save the review and rating
            Comment specialistComment = Comment.builder()
                    .customer(customer)
                    .specialist(selectedOrder.getSelectedSpecialist())
                    .order(selectedOrder)
                    .comment(comment)
                    .rating(rating)
                    .build();

            commentService.save(specialistComment);
            System.out.println("Your review and rating have been submitted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        commentMenu(customer);
    }
    public void updateComment(Customer customer) {
        try {
            // Get the comments of the customer
            List<Comment> customerComments = commentService.findAllByCustomer(customer);
            if (customerComments.isEmpty()) {
                System.out.println("No comments found for this customer!");
                commentMenu(customer);
                return;
            }

            // Display the comments
            System.out.println("Your Comments:");
            for (int i = 0; i < customerComments.size(); i++) {
                Comment comment = customerComments.get(i);
                Order order = comment.getOrder();
                System.out.println(i + 1 + ". " + "Order ID: " + order.getId() + ", Sub-Service: " + order.getSubService().getSubserviceName() + ", Specialist: " + order.getSelectedSpecialist().getLastName() + ", Rating: " + comment.getRating() + ", Comment: " + comment.getComment());
            }

            // Get the selected comment for updating
            System.out.println("Enter the number of the comment you want to update:");
            int commentChoice = Integer.parseInt(scanner.nextLine());
            if (commentChoice < 1 || commentChoice > customerComments.size()) {
                System.out.println("Invalid comment choice!");
                commentMenu(customer);
                return;
            }
            Comment selectedComment = customerComments.get(commentChoice - 1);

            // Get the updated review and rating from the customer
            System.out.print("Enter your updated review for the specialist: ");
            String updatedCommentText = scanner.nextLine();

            System.out.print("Enter an updated rating for the specialist (1 to 5): ");
            int updatedRating = Integer.parseInt(scanner.nextLine());
            if (updatedRating < 1 || updatedRating > 5) {
                System.out.println("Invalid rating! It must be between 1 and 5.");
                commentMenu(customer);
                return;
            }

            // Update the review and rating
            selectedComment.setComment(updatedCommentText);
            selectedComment.setRating(updatedRating);

            commentService.update(selectedComment, selectedComment.getId());
            System.out.println("Your review and rating have been updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        commentMenu(customer);
    }
    public void showAndDeleteComments(Customer customer) {
        try {
            // Get the comments of the customer
            List<Comment> customerComments = commentService.findAllByCustomer(customer);
            if (customerComments.isEmpty()) {
                System.out.println("No comments found for this customer!");
                commentMenu(customer);
            }

            // Display the comments
            System.out.println("Your Comments:");
            for (int i = 0; i < customerComments.size(); i++) {
                Comment comment = customerComments.get(i);
                Order order = comment.getOrder();
                System.out.println(i + 1 + ". " + "Order ID: " + order.getId() + ", Sub-Service: " + order.getSubService().getSubserviceName() + ", Specialist: " + order.getSelectedSpecialist().getLastName() + ", Rating: " + comment.getRating() + ", Comment: " + comment.getComment());
            }

            // Get the selected comment for deletion
            System.out.println("Enter the number of the comment you want to delete (or 0 to cancel):");
            int commentChoice = Integer.parseInt(scanner.nextLine());
            if (commentChoice == 0) {
                System.out.println("Operation cancelled.");
                commentMenu(customer);
            }
            if (commentChoice < 1 || commentChoice > customerComments.size()) {
                System.out.println("Invalid comment choice!");
                commentMenu(customer);
            }
            Comment selectedComment = customerComments.get(commentChoice - 1);

            // Confirm deletion
            System.out.println("Are you sure you want to delete this comment? (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                commentService.deleteComment(selectedComment);
                log.info("Comment deleted successfully!");
                commentMenu(customer);
            } else {
                System.out.println("Comment deletion cancelled.");
                commentMenu(customer);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void createNewOffer(Specialist specialist) {
        try {
            // Load the specialist with subServices
            specialist = specialistService.findByIdWithSubServices(specialist.getId());

            // Get all orders related to the specialist's sub-services
            Set<SubService> specialistSubServices = specialist.getSubServices();
            List<Order> relevantOrders = new ArrayList<>();
            for (SubService subService : specialistSubServices) {
                relevantOrders.addAll(orderService.findAllBySubServiceAndStatus(subService, OrderStatus.WAITING_FOR_SPECIALIST_OFFER));
            }

            if (relevantOrders.isEmpty()) {
                System.out.println("No relevant orders found for your specialties in the WAITING_FOR_SPECIALIST_OFFER status.");
                offerMenu(specialist);
                return; // Ensure method exits here if no relevant orders found
            }

            // Display the relevant orders
            System.out.println("Relevant Orders:");
            for (int i = 0; i < relevantOrders.size(); i++) {
                Order order = relevantOrders.get(i);
                System.out.println(i + 1 + ". " + "Order ID: " + order.getId() + ", Sub-Service: " + order.getSubService().getSubserviceName() + ", Description: " + order.getDescription());
            }

            // Get the selected order ID from the specialist
            System.out.println("Enter the number of the order you want to make an offer for:");
            int orderChoice = Integer.parseInt(scanner.nextLine());
            if (orderChoice < 1 || orderChoice > relevantOrders.size()) {
                System.out.println("Invalid order choice!");
                offerMenu(specialist);
                return; // Ensure method exits here if invalid order choice
            }
            Order selectedOrder = relevantOrders.get(orderChoice - 1);

            // Get the proposed price and duration for the offer
            System.out.println("Enter the suggested price:");
            double proposedPrice = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter the duration of the task (e.g., 2 days):");
            String duration = scanner.nextLine();

            LocalDateTime proposalDate = LocalDateTime.now();

            // Create the offer
            Offer offer = Offer.builder()
                    .order(selectedOrder)
                    .specialist(specialist)
                    .proposalDate(proposalDate)
                    .proposedPrice(proposedPrice)
                    .duration(duration)
                    .build();

            offerService.save(offer);
            System.out.println("Offer successfully registered.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in entering information: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            offerMenu(specialist); // Always return to the menu
        }
    }
    public void updateOffer(Specialist specialist) {
        try {
            // Get all offers related to the specialist
            Optional<Offer> specialistOffersOptional = offerService.findBySpecialist(specialist);
            if (specialistOffersOptional.isEmpty()) {
                System.out.println("No offers found for you to update.");
                offerMenu(specialist);
            }

            List<Offer> specialistOffers = Collections.singletonList(specialistOffersOptional.get());

            // Display the specialist's offers
            System.out.println("Your Offers:");
            for (int i = 0; i < specialistOffers.size(); i++) {
                Offer offer = specialistOffers.get(i);
                System.out.println(i + 1 + ". " + "Offer ID: " + offer.getId() + ", Order ID: " + offer.getOrder().getId() + ", Proposed Price: " + offer.getProposedPrice());
            }

            // Get the selected offer ID from the specialist
            System.out.println("Enter the number of the offer you want to update:");
            int offerChoice = Integer.parseInt(scanner.nextLine());
            if (offerChoice < 1 || offerChoice > specialistOffers.size()) {
                System.out.println("Invalid offer choice!");
                offerMenu(specialist);
            }
            Offer selectedOffer = specialistOffers.get(offerChoice - 1);

            // Get the updated proposed price and duration for the offer
            System.out.println("Enter the new suggested price:");
            double proposedPrice = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter the new duration of the task (e.g., 2 days):");
            String duration = scanner.nextLine();

            // Update the offer
            selectedOffer.setProposedPrice(proposedPrice);
            selectedOffer.setDuration(duration);

            offerService.update(selectedOffer, specialist);
            System.out.println("Offer successfully updated.");
            offerMenu(specialist);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in updating the offer. Please try again.");
            updateOffer(specialist);
        }
    }
    public void deleteOffer(Specialist specialist) {
        try {
            // Get all offers related to the specialist
            Optional<Offer> specialistOffersOptional = offerService.findBySpecialist(specialist);
            if (specialistOffersOptional.isEmpty()) {
                System.out.println("No offers found for you to delete.");
                offerMenu(specialist);
            }

            List<Offer> specialistOffers = Collections.singletonList(specialistOffersOptional.get());

            // Display the specialist's offers
            System.out.println("Your Offers:");
            for (int i = 0; i < specialistOffers.size(); i++) {
                Offer offer = specialistOffers.get(i);
                System.out.println(i + 1 + ". " + "Offer ID: " + offer.getId() + ", Order ID: " + offer.getOrder().getId() + ", Proposed Price: " + offer.getProposedPrice());
            }

            // Get the selected offer ID from the specialist
            System.out.println("Enter the number of the offer you want to delete:");
            int offerChoice = Integer.parseInt(scanner.nextLine());
            if (offerChoice < 1 || offerChoice > specialistOffers.size()) {
                System.out.println("Invalid offer choice!");
                offerMenu(specialist);
            }
            Offer selectedOffer = specialistOffers.get(offerChoice - 1);

            // Delete the offer
            offerService.deleteOffer(selectedOffer);
            System.out.println("Offer successfully deleted.");
            offerMenu(specialist);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in deleting the offer. Please try again.");
            deleteOffer(specialist);
        }
    }
}
