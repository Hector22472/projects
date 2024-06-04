/**
 * @author Hector Mendez-Garcia
 * 
 * This program simulates a society where different roles (Doctor, Farmer, Carpenter, Hunter)
 * perform tasks, face disasters, and survive or fail based on various factors.
 */
import java.util.Random;

/**
 * Enum representing different types of people in the society.
 */
enum PeopleType {
    DOCTOR,
    FARMER,
    CARPENTER,
    HUNTER
}

/**
 * Class to test the society simulation over multiple runs.
 */
class SocietyTester {
    /**
     * Main method to run the society simulation.
     * 
     * @param args Command-line arguments: <mode> <testRuns>
     */
    public static void main(String args[]) {
        int testRuns = Integer.parseInt(args[1]);
        int totalDays = 0;
        String modeString;
        // Print the mode
        System.out.println(args[0]);

        // Validate command-line arguments
        if (args.length < 3) {
            modeString = args[0];
        } else {
            System.out.println("Error: unable to parse input\nPlease try again using the arguments <mode> <testRuns>\nAvailable modes are 'Society' and 'Anarchy'.\n");
            return;
        }

        int successes = 0;
        // Run simulations and calculate statistics
        for (int i = 0; i < testRuns; i++) {
            int simLength = SocietySimulation.runSimulation(modeString);
            if (simLength >= 365) {
                successes++;
            }
            totalDays += simLength;
        }

        double avgDays = totalDays / (double) testRuns;
        double successRate = successes / (double) testRuns;
        // Print simulation results
        System.out.println("This society succeeded " + successes + " times out of " + testRuns +
                           " simulations.\nThe average number of days survived was " + avgDays);
    }
}

/**
 * Class representing the society simulation.
 */
class SocietySimulation {
    /**
     * Method to run the society simulation for a given mode.
     * 
     * @param modeString The simulation mode ('Society' or 'Anarchy').
     * @return The number of days the society survived.
     */
    public static int runSimulation(String modeString) {
        // Convert the modeString to uppercase for case-insensitive comparison
        String mode = modeString.toUpperCase();

        // Create people with different roles
        Person doctor = new Person(PeopleType.DOCTOR);
        Person farmer = new Person(PeopleType.FARMER);
        Person carpenter = new Person(PeopleType.CARPENTER);
        Person hunter = new Person(PeopleType.HUNTER);
        Person[] people = new Person[]{doctor, farmer, carpenter, hunter};
        int days = 1;
        Random random = new Random();

         // Run the simulation for up to 365 days or until all people are dead
        while (days <= 365 && (doctor.isAlive() || farmer.isAlive() || carpenter.isAlive() || hunter.isAlive())) {
            // Apply skills and simulate events for each role
            if (doctor.isAlive()) {
                if (mode.equals("ANARCHY")) {
                    if (doctor.getFood() == 1) {
                        doctor.setFood(1);
                    } else {
                        doctor.setHealth(2);
                    }
                } else {
                    if (doctor.getFood() == 1) {
                        doctor.setFood(1);
                    } else {
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setHealth(2);
                            }
                        }
                    }
                }
            }

            // Farmer
            if (farmer.isAlive()) {
                if (mode.equals("ANARCHY")) {
                    if (days % 3 == 0) {
                        farmer.setFood(3);
                    }
                } else {
                    if (days % 3 == 0) {
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setFood(3);
                            }
                        }
                    }
                }
            }

            // Carpenter
            if (carpenter.isAlive()) {
                if (mode.equals("ANARCHY")) {
                    if (carpenter.getFood() == 1) {
                        carpenter.setFood(1);
                    } else {
                        carpenter.setShelter(2);
                    }
                } else {
                    if (carpenter.getFood() == 1) {
                        carpenter.setFood(1);
                    } else {
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setShelter(1);
                            }
                        }
                    }
                }
            }

            // Hunter
            if (hunter.isAlive()) {
                int val = random.nextInt(5);
                boolean meatFound = val == 0;
                if (meatFound) {
                    if (mode.equals("ANARCHY")) {
                        hunter.setFood(2);
                    } else {
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setFood(2);
                            }
                        }
                    }
                }
            }

            // Decrement food attribute
            for (Person p : people) {
                if (p.isAlive()) {
                    p.setFood(-1);
                }
            }

            // Disaster
            int disasterVal = random.nextInt(5);
            boolean disaster = disasterVal != 0;
            if (disaster) {
                switch (disasterVal) {
                    // Hurricane
                    case 1:
                        for (Person p : people) {
                            if (p.isAlive()) {
                                if (p.getShelter() == 0) {
                                    p.setHealth(-5);
                                }
                                p.setShelter(-3);
                            }
                        }
                        break;
                    // Famine
                    case 2:
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setFood(-2);
                            }
                        }
                        break;
                    // Disease
                    case 3:
                        for (Person p : people) {
                            if (p.isAlive()) {
                                p.setHealth(-2);
                            }
                        }
                        break;
                    // Wolves
                    case 4:
                        if (mode.equals("ANARCHY")) {
                            hunter.setHealth(-1);
                            for (Person p : people) {
                                if (p.isAlive() && !p.getName().equals(PeopleType.HUNTER)) {
                                    p.setHealth(-3);
                                }
                            }
                        } else {
                            if (hunter.getHealth() != 0) {
                                for (Person p : people) {
                                    if (p.isAlive() && !p.getName().equals(PeopleType.HUNTER)) {
                                        p.setHealth(-1);
                                    }
                                }
                            } else {
                                for (Person p : people) {
                                    if (p.isAlive() && !p.getName().equals(PeopleType.HUNTER)) {
                                        p.setHealth(-3);
                                    }
                                }
                            }
                        }

                        hunter.setHealth(-1);
                        break;
                }
            }

            // Display the simulation progress
            System.out.println("********************");
            System.out.println("Day " + days + "\n");
            switch (disasterVal) {
                case 1:
                    System.out.println("Disaster: Hurricane");
                    break;
                case 2:
                    System.out.println("Disaster: Famine");
                    break;
                case 3:
                    System.out.println("Disaster: Disease");
                    break;
                case 4:
                    System.out.println("Disaster: Wolves");
                    break;
                default:
                    System.out.println("No Disaster!");
            }
            System.out.println("---------------------");

            for (Person p : people) {
                if (p.isAlive()) {
                    PeopleType type = p.getName();
                    String name = "";
                    switch (type) {
                        case DOCTOR:
                            name = "Doctor";
                            break;
                        case FARMER:
                            name = "Farmer";
                            break;
                        case CARPENTER:
                            name = "Carpenter";
                            break;
                        case HUNTER:
                            name = "Hunter";
                            break;
                    }

                    System.out.print(name + ": " + p.displayStatus() + "\n");
                }
            }

            days++;
        }

        String modeResult = mode.equals("ANARCHY") ? "Anarchy" : "Society";
        // Print the simulation outcome
        if (days > 365) {
            System.out.println("Congratulations, this society survived the simulation in " + modeResult + " mode!");
            return days;
        } else {
            System.out.println("Oh no! This society failed after " + days + " days :(");
            return days;
        }
    }
}

/**
 * Class representing an individual in the society.
 */
class Person {
    PeopleType name;
    int food;
    int health;
    int shelter;

    /**
     * Constructor to initialize a person with default attributes.
     * 
     * @param _name The type of person (Doctor, Farmer, Carpenter, Hunter).
     */
    public Person(PeopleType name) {
        this.name = name;
        food = health = shelter = 10;
    }

    /**
     * Check if the person is alive based on food and health.
     * 
     * @return True if the person is alive, false otherwise.
     */
    public boolean isAlive() {
        return this.food > 0 && this.health > 0;
    }

    /**
     * Get the type of the person.
     * 
     * @return The type of the person.
     */
    public PeopleType getName() {
        return this.name;
    }

    /**
     * Get the amount of food remaining.
     * 
     * @return amount of food remaining.
     */
    public int getFood() {
        return this.food;
    }

    /**
     * Get the amount of health remaining.
     * 
     * @return Get the amount of health remaining.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Get shelter.
     * 
     * @return shelter.
     */
    public int getShelter() {
        return this.shelter;
    }

    /**
     * Set the amount of food.
     * 
     * @set the amount of food.
     */
    public void setFood(int inc) {
        if (this.food + inc < 0) {
            this.food = 0;
        } else {
            this.food = this.food + inc;
        }
    }

    /**
     * Set the amount of health.
     * 
     * @set the amount of health.
     */
    public void setHealth(int inc) {
        if (this.health + inc < 0) {
            this.health = 0;
        } else if (this.health + inc > 10) {
            this.health = 10;
        } else {
            this.health = this.health + inc;
        }
    }

    /**
     * Set shelter.
     * 
     * @set shelter.
     */
    public void setShelter(int inc) {
        if (this.shelter + inc < 0) {
            this.shelter = 0;
        } else if (this.shelter + inc > 10) {
            this.shelter = 10;
        } else {
            this.shelter = this.shelter + inc;
        }
    }
    
    /**
     * Display the status of the person.
     * 
     * @return A string representation of the person's status.
     */
    public String displayStatus() {
        String food = String.valueOf(this.food);
        String health = String.valueOf(this.health);
        String shelter = String.valueOf(this.shelter);
        return food + ", " + health + ", " + shelter;
    }
}
