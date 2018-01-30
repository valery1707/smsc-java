package name.valery1707.smsc.message;

import name.valery1707.smsc.contact.Contact;
import name.valery1707.smsc.shared.ServerCharset;
import name.valery1707.smsc.time.DeliveryTime;

import javax.annotation.MatchesPattern;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.InetAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("WeakerAccess")
public class Message {
	public static final String ID_REGEXP =
			"^" +
			"[a-zA-Z0-9._-]{1,40}" +
			"$";
	public static final Pattern ID_PATTERN = Pattern.compile(ID_REGEXP);

	public static final String SENDER_REGEXP =
			"^" +
			"[a-zA-Z0-9 ]{1,11}" +
			"|" +
			"\\d{1,15}" +
			"$";
	public static final Pattern SENDER_PATTERN = Pattern.compile(SENDER_REGEXP);

	@NotNull
	private List<Contact> contacts = new ArrayList<>();
	@Size(max = 800)
	private String text;
	private ServerCharset charset;
	@NotNull
	private MessageType type = MessageType.Known.SMS;
	@MatchesPattern(ID_REGEXP)
	@javax.validation.constraints.Pattern(regexp = ID_REGEXP)
	private String id;
	@MatchesPattern(SENDER_REGEXP)
	@javax.validation.constraints.Pattern(regexp = SENDER_REGEXP)
	private String sender;
	@NotNull
	private Transliterate transliterate = Transliterate.Known.AS_IS;
	private Boolean tinyUrl;
	private DeliveryTime delivery;
	private Multicast multicast;
	private String fileUrl;
	private CallVoice callVoice;
	private CallParam callParam;
	private String subject;
	private Duration valid;
	private Integer splitLimit;
	private String filterCode;
	private InetAddress filterIP;

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(@Nonnull List<Contact> contacts) {
		this.contacts = requireNonNull(contacts, "Contacts must be non null");
	}

	public Message withContacts(@Nonnull List<Contact> contacts) {
		setContacts(contacts);
		return this;
	}

	public Message withContact(Contact contact) {
		this.contacts.add(contact);
		return this;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message withText(String text) {
		setText(text);
		return this;
	}

	public ServerCharset getCharset() {
		return charset;
	}

	public void setCharset(ServerCharset charset) {
		this.charset = charset;
	}

	public Message withCharset(ServerCharset charset) {
		setCharset(charset);
		return this;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(@Nonnull MessageType type) {
		this.type = requireNonNull(type, "Type must be non null");
	}

	public Message withType(@Nonnull MessageType type) {
		setType(type);
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Message withId(String id) {
		setId(id);
		return this;
	}

	public Message withId(Long id) {
		if (id != null) {
			return withId(id.toString());
		} else {
			return this;
		}
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Message withSender(String sender) {
		setSender(sender);
		return this;
	}

	public Transliterate getTransliterate() {
		return transliterate;
	}

	public void setTransliterate(@Nonnull Transliterate transliterate) {
		this.transliterate = requireNonNull(transliterate, "Transliterate must be non null");
	}

	public Message withTransliterate(@Nonnull Transliterate transliterate) {
		setTransliterate(transliterate);
		return this;
	}

	public Boolean getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(Boolean tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

	public Message withTinyUrl(Boolean tinyUrl) {
		setTinyUrl(tinyUrl);
		return this;
	}

	public DeliveryTime getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryTime delivery) {
		this.delivery = delivery;
	}

	public Message withDelivery(DeliveryTime delivery) {
		setDelivery(delivery);
		return this;
	}

	public Multicast getMulticast() {
		return multicast;
	}

	public void setMulticast(Multicast multicast) {
		this.multicast = multicast;
	}

	public Message withMulticast(Multicast multicast) {
		setMulticast(multicast);
		return this;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Message withFileUrl(String fileUrl) {
		setFileUrl(fileUrl);
		return this;
	}

	public Message withCall(CallVoice voice, CallParam param) {
		return this
				.withType(MessageType.Known.CALL)
				.withCallVoice(voice)
				.withCallParam(param);
	}

	public CallVoice getCallVoice() {
		return callVoice;
	}

	public void setCallVoice(CallVoice callVoice) {
		this.callVoice = callVoice;
	}

	public Message withCallVoice(CallVoice callVoice) {
		setCallVoice(callVoice);
		return this;
	}

	public CallParam getCallParam() {
		return callParam;
	}

	public void setCallParam(CallParam callParam) {
		this.callParam = callParam;
	}

	public Message withCallParam(CallParam callParam) {
		setCallParam(callParam);
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Message withSubject(String subject) {
		setSubject(subject);
		return this;
	}

	public Duration getValid() {
		return valid;
	}

	public void setValid(Duration valid) {
		this.valid = valid;
	}

	public Message withValid(Duration valid) {
		setValid(valid);
		return this;
	}

	public Integer getSplitLimit() {
		return splitLimit;
	}

	public void setSplitLimit(Integer splitLimit) {
		this.splitLimit = splitLimit;
	}

	public Message withSplitLimit(Integer splitLimit) {
		setSplitLimit(splitLimit);
		return this;
	}

	public String getFilterCode() {
		return filterCode;
	}

	public void setFilterCode(String filterCode) {
		this.filterCode = filterCode;
	}

	public Message withFilterCode(String filterCode) {
		setFilterCode(filterCode);
		return this;
	}

	public InetAddress getFilterIP() {
		return filterIP;
	}

	public void setFilterIP(InetAddress filterIP) {
		this.filterIP = filterIP;
	}

	public Message withFilterIP(InetAddress filterIP) {
		setFilterIP(filterIP);
		return this;
	}
}
