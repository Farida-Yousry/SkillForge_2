package skillForge;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	private JButton btnAdd,btnEdit,btnDelete,btnSave;
	
	private JTextField titleField,resoursesField;
	private JTextArea contentArea;
	private CourseManager manager;
	
	

	/**
	 * Create the panel.
	 */
	public InstructorLessonPannel(Course course,CourseManager manager) {
		this.course=course;
		this.manager=manager;
		setLayout(new BorderLayout());
		
		lessonModel=new DefaultListModel<>();
		if(course.getLessons()!=null) {
		for(Lesson e : course.getLessons()) {
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
		    JPanel panel=new JPanel(new GridLayout(3,1,8,8));
		    
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
       
       mainPanel.add(btnAdd);
       mainPanel.add(btnEdit);
       mainPanel.add(btnDelete);
       
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
					course.addLesson(addedLesson);
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
				 lessonModel.removeElement(selected);
				
			   JOptionPane.showMessageDialog(this,"Lesson deleted Successfully");
			    }
			   else
				JOptionPane.showMessageDialog(this,"Lesson Not Found");
				
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

	