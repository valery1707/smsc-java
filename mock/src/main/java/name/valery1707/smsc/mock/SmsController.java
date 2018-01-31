package name.valery1707.smsc.mock;

import name.valery1707.smsc.mock.balance.BalanceController;
import name.valery1707.smsc.mock.message.MessageController;
import spark.Route;

public interface SmsController extends Route {
	String path();

	void setDatabase(Database database);

	/**
	 * Poor man's DI
	 */
	enum Implementations {
		BALANCE(new BalanceController()),
		MESSAGE(new MessageController()),
		//Formatting
		;
		private final SmsController controller;

		Implementations(SmsController controller) {
			this.controller = controller;
		}

		public SmsController controller() {
			return controller;
		}
	}
}
