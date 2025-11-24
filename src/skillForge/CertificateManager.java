package skillForge;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.font.PDFont;


public class CertificateManager {
	// attributes
 private QuizManager qManager;
 private CourseManager cManager;
 private Database db;
 
//constructor
 public CertificateManager(QuizManager qManager, CourseManager cManager, Database db) {
	this.qManager = qManager;
	this.cManager = cManager;
	this.db = db;
 }
 public String generateNewId() {
	String id=String.format("CR%05d",System.currentTimeMillis()%100000);
	 return id;
	
}
 public boolean isCourseCompleted(String courseId,String studentId) {
	Course course= cManager.getCourseById(courseId);
	 ArrayList<Lesson> lessons= course.getLessons();
	 boolean isPassed = true;
	 for(Lesson l : lessons) {
		 if(!qManager.passed(studentId,l.getLessonId())){
				 isPassed= false;
		         break;}
	 }
	 return isPassed;
 }
 public Certificate generateCertificate(String courseId,String studentId) {
	 if(!isCourseCompleted(courseId,studentId))return null;
	 if(getCertificate(courseId,studentId) != null) return null;
	
	 Certificate c=new Certificate(generateNewId(),studentId,courseId,new Date());
	 ArrayList<User> users = db.loadUsers();
	
     boolean findUser = false;
     Student s=null;
	 for(User u : users) {
		 if(u.getUserId().equals(studentId)) {
			
			findUser = true;
			if(u instanceof Student)s=(Student)u;
			
			 break;
			 }
	     }
	// ArrayList<Certificate> certs=s.getCertificate();
	 if(!findUser)
		 return null;
	 ArrayList<Course> courses = db.loadCourses();
	 Course course=null;
	 
	 for(Course cs : courses) {
		 if(cs.getCourseId().equals(courseId)) {
			 cs.addCertificate(c);
			 course=cs;
			 break;
		 }
	 }
		
	 
	/* Course core = cManager.getCourseById(courseId);
	 if(core != null)
		 core.addCertificate(c);*/
	 
	 if(s!=null&&course!=null) {//generateCertificatePdf(c,s,course);
	 s.addCertificate(c);
	 }
	// s.setCertificate(certs);
	 db.saveToUsersFile(users);
	 db.saveToCourseFile(courses);

	
	 
	 return c;
 }

 
 public Certificate getCertificate(String courseId,String studentId) {
	 ArrayList<User> users = db.loadUsers();
	 for(User u : users) {
		 if((u instanceof Student)&&u.getUserId().equals(studentId)) {
			 Student s=(Student)u;
	 for(Certificate c: s.getCertificate()) {
		 if(c.getCourseId().equals(courseId)) {
			 return c;
		 }
	 }
		 }
	 }
	 return null;
 }
	
 public void generateCertificatePdf(Certificate c,Student s,Course course) {
	

	 String name=s.getUserId();
	 String title=course.getTitle();
	 String instId=c.getCertificateId();
	 String cId=course.getCourseId();
	 
	 String directoryPath="Certificates";
	 String date=new SimpleDateFormat("MMMM d,yyyy").format(c.getReleaseDate());
	
	String fileName = c.getCertificateId() + "_" + s.getUserId() + ".pdf";
	String fullPath = directoryPath + File.separator + fileName;
	try ( PDDocument document=new PDDocument()) {

   PDPage page=new PDPage(PDRectangle.A4);
    document.addPage(page);
		File dir=new File(directoryPath);
		
		if(!dir.exists()) {
			dir.mkdirs();
			
		}
		try (PDPageContentStream content = new PDPageContentStream(document, page)){
			
		
	//	PDRectangle box=page.getMediaBox();
			
			float borderMargin = 40;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();	
            content.setLineWidth(3f);
            
            content.moveTo(borderMargin, borderMargin);
            content.lineTo(pageWidth - borderMargin, borderMargin);
            content.lineTo(pageWidth - borderMargin, pageHeight - borderMargin); 
            content.lineTo(borderMargin, pageHeight - borderMargin); 
            content.closePath(); 
            content.stroke();
	
		int y=600;
		String cTitle="CERTIFICATE OF COMPLETION";
		float centre=centerText(cTitle,page,new PDType1Font(FontName.TIMES_BOLD),28);
		content.beginText();
		content.setFont(new PDType1Font(FontName.TIMES_BOLD), 28);
		content.newLineAtOffset(centre,y);
		content.showText(cTitle);
		content.endText();
		
		y-=50;
		String awardedText = " Proudly Presented to";
		float awardedCentre = centerText(awardedText, page, new PDType1Font(FontName.TIMES_ROMAN), 24);
        content.beginText();
        content.setFont(new PDType1Font(FontName.TIMES_ROMAN), 24);
        content.newLineAtOffset(awardedCentre, y);
        content.showText(awardedText);
        content.endText();
        y-=50;
		float sName=centerText(name,page,new PDType1Font(FontName.HELVETICA_BOLD_OBLIQUE),48);
		content.beginText();
		content.setFont(new PDType1Font(FontName.HELVETICA_BOLD_OBLIQUE), 48);
		content.newLineAtOffset(sName,y);
		content.showText(name.toUpperCase());
		content.endText();
		y-=50;
		String textt=" For Completing ";
		float text=centerText(textt,page,new PDType1Font(FontName.TIMES_ROMAN),24);
		content.beginText();
		content.setFont(new PDType1Font(FontName.TIMES_ROMAN), 24);
		content.newLineAtOffset(text,y);
		content.showText(textt);
		content.endText();
		y -= 50;
		float sCourseId=centerText(cId,page,new PDType1Font(FontName.HELVETICA_BOLD_OBLIQUE),48);
		content.beginText();
		content.setFont(new PDType1Font(FontName.HELVETICA_BOLD_OBLIQUE), 48);
		content.newLineAtOffset(sCourseId,y);
		content.showText(cId.toUpperCase());
		content.endText();
      
       /* float texxtt = centerText(title, page, new PDType1Font(FontName.TIMES_BOLD), 30); // Larger course title
        content.beginText();
        content.setFont(new PDType1Font(FontName.TIMES_BOLD), 30);
        content.newLineAtOffset(text, y);
        content.showText(title.toUpperCase());
        content.endText();
        float margin = 80;
        float pageWidthh = page.getMediaBox().getWidth();*/
		float margin = 80;
        y = 180;
        content.setLineWidth(1.5f); 
        content.moveTo(margin, y);
        content.lineTo(pageWidth - margin, y);
        content.stroke();
        y -= 40;
        
		String dTxt="Release Date: "+date;
		content.beginText();
		content.setFont(new PDType1Font(FontName.TIMES_ROMAN), 14);
		content.newLineAtOffset(margin,y);
		content.showText(dTxt);
		content.endText();
		
String info="Certificate ID: "+instId;
content.beginText();
content.setFont(new PDType1Font(FontName.TIMES_ROMAN), 14);

content.newLineAtOffset(350, y);
content.showText(info);
content.endText();
		}

		document.save(directoryPath + File.separator + c.getCertificateId() + "_" + s.getUserId() + ".pdf");
		//document.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
 
 }
private float centerText(String text,PDPage page,PDFont font,int size) throws IOException {
	float width=font.getStringWidth(text)/1000*size;
	PDRectangle pSize=page.getMediaBox();
	float pWidth=pSize.getWidth();
	return(pWidth-width)/2;
}


}
