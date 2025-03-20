package dataFetch;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

    @Test
    public void dynamicPagination() throws InterruptedException {
        // Setup WebDriverManager
        WebDriverManager.chromedriver().setup();
        
        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Open OpenCart admin login page
        driver.get("https://www.ecomdeveloper.com/demo/admin/index.php");
        driver.manage().window().maximize();
        
        // Locate username field (Fixed XPath)
        WebElement username = driver.findElement(By.xpath("//input[@id='input-username']"));
        username.clear();
        username.sendKeys("demoadmin");
        
        // Locate password field (Fixed XPath)
        WebElement password = driver.findElement(By.xpath("//input[@id='input-password']"));
        password.clear();
        password.sendKeys("demopass");
        
        // Locate and click login button (Fixed XPath)
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        
        
        driver.findElement(By.xpath("//a[@class='parent collapsed'][normalize-space()='Customers']")).click();  // Customers Parent
        
        driver.findElement(By.xpath("//ul[@id='collapse9']//a[contains(text(),'Customers')]")).click(); // Customers Child
        
        String textString =  driver.findElement(By.xpath("//div[contains(text(), 'Showing')]")).getText();
        
        
        
        int total_pages = Integer.parseInt(textString.substring(textString.indexOf("(") +1, textString.indexOf("Pages")-1));
        
        // Loop to move in each page
        
        for(int i =1; i<=total_pages; i++) {
        	
        	if(i>1) {
        		
       WebElement active_Page = driver.findElement(By.xpath("//ul[@class='pagination']//*[text()="+i+"]"));
        
       JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
       active_Page.click();    	
       Thread.sleep(1000);
        	}
        	
        	// Reading Page
        	
        	List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr"));
        	int noOfRows = rows.size(); // This will give you the number of rows
        	
        	for(int r=1; r<=noOfRows; r++) {
        		
        		String CustomerName=driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tr["+r+"]/td[2]")).getText();
        		String email= driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tr["+r+"]/td[3]")).getText();
        		String 	status =driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tr["+r+"]/td[5]")).getText();
        		
        		
        		System.out.println(CustomerName +"\t"+ email + "\t" + status);
        		
        	}

        	
        }
        
        
        
   
        // Close the browser
        driver.quit();
    }
}
