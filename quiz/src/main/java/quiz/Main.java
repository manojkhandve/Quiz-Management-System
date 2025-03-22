package quiz;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.createQuestionsTable();
        User user = null;

        while (true) {
            System.out.println("\n--- Quiz System ---");
            System.out.println("1. Start Quiz");
            System.out.println("2. View User Score");
            System.out.println("3. Add Question");
            System.out.println("4. View All Questions");
            System.out.println("5. Update Question");
            System.out.println("6. Delete Question");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    if (user == null) {
                        System.out.print("Enter your username: ");
                        String username = scanner.nextLine();
                        user = new User(username);
                    }
                    startQuiz(scanner, quizDAO, user);
                    break;
                case 2:
                    if (user != null) {
                        System.out.println("Your Score: " + user.getScore());
                    } else {
                        System.out.println("Please start the quiz first.");
                    }
                    break;
                case 3:
                    addQuestion(scanner, quizDAO);
                    break;
                case 4:
                    viewAllQuestions(quizDAO);
                    break;
                case 5:
                    updateQuestion(scanner, quizDAO);
                    break;
                case 6:
                    deleteQuestion(scanner, quizDAO);
                    break;
                case 7:
                    System.out.println("Exiting Quiz System...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void startQuiz(Scanner scanner, QuizDAO quizDAO, User user) {
        List<Question> questions = quizDAO.getAllQuestions();
       
        int index=1;
        for (Question q : questions) {
        	System.out.println(index + ") " + q.getQuestion()); 
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());
            System.out.print("Your answer (A/B/C/D): ");
            String answer = scanner.nextLine();
            index++;
            System.out.println();

            if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                
                user.incrementScore();
            }
        }
        
        System.out.println("Your final score: " + user.getScore());
    }

    private static void addQuestion(Scanner scanner, QuizDAO quizDAO) {
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter option A: ");
        String optionA = scanner.nextLine();
        System.out.print("Enter option B: ");
        String optionB = scanner.nextLine();
        System.out.print("Enter option C: ");
        String optionC = scanner.nextLine();
        System.out.print("Enter option D: ");
        String optionD = scanner.nextLine();
        System.out.print("Enter correct answer (A/B/C/D): ");
        String correctAnswer = scanner.nextLine();

        Question newQuestion = new Question(0, question, optionA, optionB, optionC, optionD, correctAnswer);
        quizDAO.addQuestion(newQuestion);
        System.out.println("Question added successfully!");
    }
    
    
    

    private static void viewAllQuestions(QuizDAO quizDAO) {
        List<Question> questions = quizDAO.getAllQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        int index = 1; 
        for (Question q : questions) {
        	System.out.println();
            System.out.println(index + ") " + q.getQuestion());  
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());
            System.out.println("Correct Answer: " + q.getCorrectAnswer());
            System.out.println();  
            index++;
        }
    }


    private static void updateQuestion(Scanner scanner, QuizDAO quizDAO) {
        System.out.print("Enter the ID of the question to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  

        System.out.print("Enter the new question: ");
        String question = scanner.nextLine();

        System.out.print("Enter option A: ");
        String optionA = scanner.nextLine();
        System.out.print("Enter option B: ");
        String optionB = scanner.nextLine();
        System.out.print("Enter option C: ");
        String optionC = scanner.nextLine();
        System.out.print("Enter option D: ");
        String optionD = scanner.nextLine();
        System.out.print("Enter correct answer (A/B/C/D): ");
        String correctAnswer = scanner.nextLine();

        quizDAO.updateQuestion(id, question, optionA, optionB, optionC, optionD, correctAnswer);
    }

    private static void deleteQuestion(Scanner    scanner, QuizDAO quizDAO) {
        System.out.print("Enter the ID of the question to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  
        quizDAO.deleteQuestion(id);
    }
}
