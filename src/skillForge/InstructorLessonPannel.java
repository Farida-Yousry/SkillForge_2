package skillForge;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class InstructorLessonPannel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList<Lesson> lessons;
	private DefaultListModel<Lesson> lessonModel;
	private Course course;
	
	private JButton btnAdd,btnEdit,btnDelete,btnAddQuiz;
	
	private JTextField titleField,resoursesField;
	private JTextArea contentArea;
	private JTextArea contentQuiz;
	private CourseManager manager;
	private QuizManager qManager;
	
	

	/**
	 * Create the panel.
	 */
	public InstructorLessonPannel(Course course,CourseManager manager) {
		this.course=course;
		this.manager=manager;
		this.qManager = new QuizManager(manager);
		
		setLayout(new BorderLayout());
		
		lessonModel=new DefaultListModel<>();
		if(course.getLessons()!=null) {
		for(Lesson e : course.getLessons()) {
			Quiz qq = qManager.getQuizByLessonId(e.getLessonId());
			if(qq!=null) {
				e.setQuiz(qq);
			}
			lessonModel.addElement(e);
		}}
		
		lessons = new JList<>(lessonModel);
		lessons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		lessons.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting())
		    load();
			}
		});
		    add(new JScrollPane(lessons),BorderLayout.WEST);
		    JPanel panel=new JPanel(new GridLayout(4,1,8,8));
		    
      titleField =new JTextField();
      contentArea = new JTextArea(6,24);
      resoursesField=new JTextField();

      
		    
      panel.add(createPanel("Title",titleField));
	  panel.add(createPanel("contents",new JScrollPane(contentArea)));
	  panel.add(createPanel("Resources(seperated by a comma)",resoursesField));
	 
	  
	  add(panel,BorderLayout.CENTER);
	  
	  JPanel mainPanel=new JPanel(new FlowLayout());
	  
		    
       btnAdd=new JButton("Add Lesson");
       btnEdit=new JButton("Edit Lesson");
       btnDelete=new JButton("Delete Lesson");
       btnAddQuiz=new JButton("Add Quiz");
       
       mainPanel.add(btnAdd);
       mainPanel.add(btnEdit);
       mainPanel.add(btnDelete);
       mainPanel.add(btnAddQuiz);
       
       add(mainPanel,BorderLayout.SOUTH);
       
   	 btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			addLesson();
		}
	    
   	});
	btnEdit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			editLesson();
		}
	    
   	});
	btnDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			deleteLesson();
		}
	    
   	});
   	
	btnAddQuiz.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			addQuiz();
		}
	    
   	});
   	
   	
	}
	
	private JPanel createPanel(String text,Component element) {
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(new JLabel(text),BorderLayout.NORTH);
		panel.add(element,BorderLayout.CENTER);
		return panel;
		
	}
	   private void addLesson() {
			String title = titleField.getText().trim();
			String content = contentArea.getText().trim();
			String resources = resoursesField.getText().trim();
		
				
			if(title.isEmpty()) {
				JOptionPane.showMessageDialog(this,"Please enter a title");
				return;
			}
				ArrayList<String> list = new ArrayList<>();
				if(!resources.isEmpty()) {
					for(String e : resources.split(",")) {
						if(!e.trim().isEmpty())
						list.add(e.trim());
					}
				}

				String lessonId = String.format("L%05d",System.currentTimeMillis()%100000);
				Lesson addedLesson=new Lesson(lessonId,title,content,list);
				
				boolean isSaved=manager.addLessonToCourse(course.getCourseId(), addedLesson);
				if(isSaved) {
					//course.addLesson(addedLesson);
				lessonModel.addElement(addedLesson);
				titleField.setText("");
				contentArea.setText("");
				resoursesField.setText("");
				
				JOptionPane.showMessageDialog(this,"Lesson Added Successfully");}
				else {
					JOptionPane.showMessageDialog(this,"Can not add lesson");
				}
			}
			private void editLesson() {
				Lesson selected=lessons.getSelectedValue();
				if(selected==null) {
					JOptionPane.showMessageDialog(this,"Select a lesson First");
					return;
				}
				String title = titleField.getText().trim();
				String content = contentArea.getText().trim();
				String resources = resoursesField.getText().trim();
				
				
				if(title.isEmpty()) {
					JOptionPane.showMessageDialog(this,"Please enter a title");
					return;
				}
				
				ArrayList<String> list = new ArrayList<>();
				if(!resources.isEmpty()) {
					for(String e : resources.split(",")) {
						if(!e.trim().isEmpty())
						list.add(e.trim());
					}
				}
			
				selected.setTitle(title);
				selected.setContent(content);
				selected.setResources(list);
				
				
				manager.editCourse(course.getCourseId(),course.getTitle(),course.getDescription());
				lessons.repaint();
				
				JOptionPane.showMessageDialog(this,"Lesson Updated Successfully");
				
			}
			private void deleteLesson() {
				Lesson selected=lessons.getSelectedValue();
				if(selected==null) {
					JOptionPane.showMessageDialog(this,"Select a lesson be deleted");
					return;
				}
				int confirm=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this Lesson?","Confirm",JOptionPane.YES_NO_OPTION);
			    if(confirm == JOptionPane.YES_OPTION) {
			    //  boolean deleted=course.deleteLesson(selected.getLessonId());
			      boolean deletedd=manager.deleteLessonFromCourse(course.getCourseId(),selected.getLessonId());
			   if(deletedd) {
				   course.deleteLesson(selected.getLessonId());
				 lessonModel.removeElement(selected);
				
			   JOptionPane.showMessageDialog(this,"Lesson deleted Successfully");
			    }
			   else
				JOptionPane.showMessageDialog(this,"Lesson Not Found");
				
			}
	   }
			private void addQuiz() {
			 	Lesson selected=lessons.getSelectedValue();
				if(selected==null) {
					JOptionPane.showMessageDialog(this,"Select a lesson to add/edit a Quiz for.");
					return;
				}
                
               
                
                String questionBody = JOptionPane.showInputDialog(this, "Enter Question :", 
                                                                 "Add Question for: " + selected.getTitle(), JOptionPane.PLAIN_MESSAGE);
                if (questionBody == null || questionBody.trim().isEmpty()) return;

                String choicesStr = JOptionPane.showInputDialog(this, "Enter Choices (comma-separated):", 
                                                               "Add Choices", JOptionPane.PLAIN_MESSAGE);
                if (choicesStr == null || choicesStr.trim().isEmpty()) return;
                
                ArrayList<String> choices = new ArrayList<>(Arrays.asList(choicesStr.split(",")));
                choices.removeIf(String::isEmpty); 
                
               
                StringBuilder choiceList = new StringBuilder();
                for (int i = 0; i < choices.size(); i++) {
                    choiceList.append((char)('A' + i)).append(": ").append(choices.get(i).trim()).append("\n");
                }
                
                String answerLetter = JOptionPane.showInputDialog(this, 
                    "Choices:\n" + choiceList.toString() + "\nEnter Correct Answer Letter (e.g., A, B, C):", 
                    "Correct Answer", 
                    JOptionPane.PLAIN_MESSAGE
                );
                
                if (answerLetter == null || answerLetter.trim().isEmpty()) return;
                
                int correctChoiceIndex = -1;
                try {
                   
                    String letter = answerLetter.trim().toUpperCase().substring(0, 1);
                    correctChoiceIndex = letter.charAt(0) - 'A';
                    
                    if (correctChoiceIndex < 0 || correctChoiceIndex >= choices.size()) {
                        throw new IllegalArgumentException("Answer letter out of range of choices.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid answer letter entered. Please use A, B, C, etc., matching the number of choices.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                


                try {
                    String lessonId = selected.getLessonId();
                    
                    
                    Question newQuestion = new Question(
                        questionBody.trim(), 
                        choices, 
                        correctChoiceIndex 
                    );
                    
                    ArrayList<Question> questions = new ArrayList<>();
                    questions.add(newQuestion);
                    
                   
                    Quiz newQuiz = new Quiz(lessonId, questions);

                    selected.setQuiz(newQuiz);

                    
                    qManager.addQuiz( newQuiz);
                    manager.editCourse(course.getCourseId(),course.getTitle(), course.getDescription());
                    
             
                    load(); 
                    JOptionPane.showMessageDialog(this, "Quiz created and saved successfully for " + selected.getTitle() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error creating Quiz: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
			}
				
				
	   
             private void load(){
            	 Lesson selected = lessons.getSelectedValue();
            	 if(selected==null) {
     				titleField.setText("");
    				contentArea.setText("");
    				resoursesField.setText("");
    				
 					//JOptionPane.showMessageDialog(this,"Please select a lesson");
 					return;
 				}
            	 Quiz quiz = selected.getQuiz();
            	 
            	 titleField.setText(selected.getTitle());
            	 contentArea.setText(selected.getContent());
            	 
            	ArrayList<String >resourses=selected.getResources();
            	if(resourses==null||resourses.isEmpty()) {
            		resoursesField.setText("");
            	}
            	else {
            		resoursesField.setText(String.join(",", resourses));
            	}
            	 
             }
             

	}

	