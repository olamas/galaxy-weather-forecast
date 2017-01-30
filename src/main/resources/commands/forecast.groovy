package commands

import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory
import com.galaxy.weather.commands.ForecastCommand

class custom {

    @Usage("Galaxy Weather Forecast: ")
    @Command
    def main(InvocationContext context) {
        
        BeanFactory beanFactory = (BeanFactory) context.getAttributes().get("spring.beanfactory");
        ForecastCommand forecastCommand = beanFactory.getBean(ForecastCommand.class);
        forecastCommand.execute();     
        return "Hello"
    }
}