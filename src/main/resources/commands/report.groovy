package commands

import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory
import com.galaxy.weather.commands.ReportCommand

class custom {

    @Usage("Galaxy Weather Forecast: Retrieve galaxy forecast Report")
    @Command
    def main(InvocationContext context) {
    
        BeanFactory beanFactory = (BeanFactory) context.getAttributes().get("spring.beanfactory");
        ReportCommand reportCommand = beanFactory.getBean(ReportCommand.class);
        return  reportCommand.execute();     
        
    }
}