package com.lance.motiondesigninterview;

import com.lance.motiondesigninterview.domainObjects.Pack;
import com.lance.motiondesigninterview.domainObjects.PackingCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MotionDesignInterviewApplication implements CommandLineRunner {
	private final PackingCommandParser packingCommandParser;
	private final PackPlanner packer;

	public MotionDesignInterviewApplication(PackingCommandParser packingCommandParser, PackPlanner packer) {
		this.packingCommandParser = packingCommandParser;
		this.packer = packer;
	}

	public static void main(String[] args) {
		SpringApplication.run(MotionDesignInterviewApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("This tool provides to packing algorithm. Please type in packing criteria and items in the following format.\n" +
				"#Input format:\n" +
				"[Sort order],[max pieces per pack],[max weight per pack]\n" +
				"[item id],[item length],[item quantity],[piece weight]\n" +
				"[item id],[item length],[item quantity],[piece weight]\n" +
				"[item id],[item length],[item quantity],[piece weight]\n" +
				"Please past your input below and type Enter key:\n");
		PackingCommand packingCommand = this.packingCommandParser.parseCommand(System.in);
		List<Pack> packs = packer.packItems(packingCommand);
		packs.forEach(Pack::print);
	}
}
