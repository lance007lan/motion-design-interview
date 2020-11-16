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
	private final Packer packer;

	public MotionDesignInterviewApplication(PackingCommandParser packingCommandParser, Packer packer) {
		this.packingCommandParser = packingCommandParser;
		this.packer = packer;
	}

	public static void main(String[] args) {
		SpringApplication.run(MotionDesignInterviewApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PackingCommand packingCommand = this.packingCommandParser.parseCommand(System.in);
		List<Pack> packs = packer.packItems(packingCommand);
		packs.forEach(Pack::print);
	}
}
