package quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private static final String URL = "jdbc:postgresql://localhost:2004/pdea";
    private static final String USER = "postgres";
    private static final String PASSWORD = "manojk707";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createQuestionsTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS questions (" +
                                  "id SERIAL PRIMARY KEY, " +
                                  "question VARCHAR(255) NOT NULL, " +
                                  "option_a VARCHAR(100) NOT NULL, " +
                                  "option_b VARCHAR(100) NOT NULL, " +
                                  "option_c VARCHAR(100) NOT NULL, " +
                                  "option_d VARCHAR(100) NOT NULL, " +
                                  "correct_answer VARCHAR(100) NOT NULL" +
                                  ");";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableQuery);
            System.out.println("Table 'questions' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_answer")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }


    public void addQuestion(Question question) {
        String query = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptionA());
            stmt.setString(3, question.getOptionB());
            stmt.setString(4, question.getOptionC());
            stmt.setString(5, question.getOptionD());
            stmt.setString(6, question.getCorrectAnswer());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(int id, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        String query = "UPDATE questions SET question = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_answer = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, question);
            stmt.setString(2, optionA);
            stmt.setString(3, optionB);
            stmt.setString(4, optionC);
            stmt.setString(5, optionD);
            stmt.setString(6, correctAnswer);
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int id) {
        String query = "DELETE FROM questions WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowDeleted=stmt.executeUpdate();
           if(rowDeleted>0) {
           	System.out.println("Question deleted Successfully ");
            }
           else {
            	System.out.println(" No Question found with ID : " + id);
           }
         
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 