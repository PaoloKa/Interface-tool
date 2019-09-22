package com.rs.cache.loaders;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.interfaces.ComponentConstants;
import com.logging.LogFactory;

import com.interfaces.ComponentPosition;
import com.interfaces.InterfaceUtils;
import com.rs.cache.Cache;

/**
 * Interface tool
 * paolo 19/07/2019
 * #Shnek6969
 */
public class ComponentDefinition {

	public static ComponentDefinition[][] icomponentsdefs = new ComponentDefinition[getInterfaceDefinitionsSize()][];
	private static IComponentSettings GLOBAL_SETTINGS = new IComponentSettings(0, -1);
    private static final Logger logger = LogFactory.createLogger(ComponentDefinition.class.getName());
	/**
	 * positions
	 */
	public int positionX;
	public int positionY;
	public int width;
	public int height;
	public Object[] anObjectArray4680;
	public int anInt4682;
	public String[] rightclickOptions;
	public Object[] anObjectArray4688;
	public boolean hidden;
	public int layerHeight;
	public int parentId;
	public int baseWidth;
	public int anInt4697;
	public int targetOverCursor;
	public Object[] anObjectArray4701;
	public int[] anIntArray4705;
	public Object[] onMouseHoverScript;
	public int anInt4708;
	public int anInt4709 = 0;
	public boolean shadow;
	public Object[] 	anObjectArray4711;
	public byte aspectXType;
	public boolean lineDirection;
	public int baseHeight;
	public int anInt4728;
	public Hashtable aHashTable4823;
	public IComponentSettings activeProperties;
	public boolean vFlip;
	public byte[] aByteArray4733;
	public int layerWidth;
	public byte aspectHeightType;
	public Object[] anObjectArray4742;
	public boolean hFlip;
	public int borderThickness;
	public byte aspectWidthType;
	public Object[] anObjectArray4751;
	public int lineWidth;
	public Object[] anObjectArray4753;
	public int color;
	public int transparency;
	public Object[] onLoadScript;
	public int fontId;
	public int mouseOverCursor;
	public String name;
	public int multiline;
	public Object[] anObjectArray4768;
	public boolean filled;
	public Object[] anObjectArray4770;
	public Object[] anObjectArray4771;
	public int animationId;
	public Object[] anObjectArray4774;
	public Object[] anObjectArray4775;
	public String Name;
	public boolean clickMask;
	public String aString4784;
	public String aString4786;
	public int zoom;
	public Object[] anObjectArray4788;
	public int[] anIntArray4789;
	public String text;
	public int anInt4795;
	public int anInt4796;
	public int anInt4797;
	public int anInt4800;
	public Object[] anObjectArray4803;
	public int[] anIntArray4805;
	public byte[] aByteArray4806;
	public Object[] anObjectArray4807;
	public int width2;
	public int contentType;
	public int anInt4815;
	public int basePositionY;
	public Object[] onMouseLeaveScript;
	public int spriteId;
	public int anInt4821;
	public int ihash;
	public int textVerticalAli;
	public int[] anIntArray4829;
	public boolean aBoolean4832;
	public int[] anIntArray4833;
	public Object[] popupScript;
	public int textHorizontalAli;
	public int[] configs;
	public int targetLeaveCursor;
	public int type;
	public int anInt4842;
	public int anInt4849;
	public int basePositionX;
	public byte aspectYType;
	public Object[] anObjectArray4852;
	public Object[] anObjectArray4856;
	public boolean disableHover;
	public int anInt4860;
	public boolean repeat_;
	public int[] opCursors;
	public int modelId;
	public boolean aBoolean4865;

	public int componentId;
	public int interfaceId;

	/**
	 * returns a single component from an interface
	 * @param interfaceId  interface id you need
	 * @param component component if you need
	 * @return
	 */
	public static ComponentDefinition getInterfaceComponent(int interfaceId,
															int component) {
		ComponentDefinition[] inter = getInterface(interfaceId);
		if (inter == null || component >= inter.length){
			logger.log(Level.SEVERE, "Component "+component+ " from "+interfaceId+" is null.");
			return null;
		}
		return inter[component];
	}

	public static ComponentDefinition[] getInterface(int id) {
		return getInterface(id, false);
	}
	/**
	 * returns an array of components aka the interface
	 * @param id
	 * @return
	 */
	public static ComponentDefinition[] getInterface(int id, boolean reload) {
		if (id >= icomponentsdefs.length)
			return null;
		if (icomponentsdefs[id] == null || reload) {
			icomponentsdefs[id] = new ComponentDefinition[getInterfaceDefinitionsComponentsSize(id)];
			for (int i = 0; i < icomponentsdefs[id].length; i++) {
				byte[] data = Cache.STORE.getIndexes()[3].getFile(id, i);
				if (data == null) {
                    logger.log(Level.SEVERE, "Component "+i+ " from interface "+id+" is null, on initial load.");
				} else {
					ComponentDefinition defs = icomponentsdefs[id][i] = new ComponentDefinition();
					defs.ihash = i + (id << 16);
					defs.decode(new InputStream(data), i, id);
				}
            }
		}
		return icomponentsdefs[id];
	}

	public static int getInterfaceDefinitionsSize() {
		return Cache.STORE.getIndexes()[3].getLastArchiveId() +1;
	}

	public static int getInterfaceDefinitionsComponentsSize(int interfaceId) {
		return Cache.STORE.getIndexes()[3].getLastFileId(interfaceId) + 1;
	}
	/**

	 * uncoding
	 * @return  encoded byte array
	 */
	public byte[] encode(){
		OutputStream out = new OutputStream();
		out.writeByte(newInt);
		out.writeByte(this.type);
		if ((type & 0x80 ^ 0xffffffff) != -1) {//if(type != 0){
			out.writeString(this.name);
		}
		out.writeShort(this.contentType);
		out.writeShort(this.basePositionX);
		out.writeShort(this.basePositionY);
		out.writeShort(this.baseWidth);
		out.writeShort(this.baseHeight);

		out.writeByte(this.aspectWidthType);
		out.writeByte(this.aspectHeightType);
		out.writeByte(this.aspectXType);
		out.writeByte(this.aspectYType);
		if(parentId == -1  || parentId == 65535)
			out.writeShort(65535);
		else
			out.writeShort(this.parentId);// - (ihash & ~0xffff));
		if(this.hidden)
			out.writeByte(1);
		else
			out.writeByte(0);
		/**
		 * container
		 */
		if(type == ComponentConstants.CONTAINER){
			out.writeShort(this.layerWidth);
			out.writeShort(this.layerHeight);
			if((newInt ^ 0xffffffff) > -1) {
				if(disableHover)
					out.writeByte(1);
				else
					out.writeByte(0);
			}
		}
		/**
		 * sprites
		 */
		if(type == ComponentConstants.SPRITE){
			out.writeInt(this.spriteId);
			out.writeShort(this.anInt4728);
			out.writeByte(flag);
			out.writeByte(this.transparency);
			out.writeByte(this.borderThickness);
			out.writeInt(anInt4796);
			if(this.vFlip)
				out.writeByte(1);//out.writeByte(this.aBoolean4732);
			else
				out.writeByte(0);
			if(this.hFlip)
				out.writeByte(1);
			else
				out.writeByte(0);
			out.writeInt(this.color);
			if ((newInt ^ 0xffffffff) <= -4) {
				if(this.clickMask)
					out.writeByte(1);
				else
					out.writeByte(0);
			}
		}
		/**
		 *  models
		 */
		if(type == 6){
			if ((ihash >> 16) > 1144)
				out.writeBigSmart(modelId);
			else
				out.writeShort(modelId);
			out.writeByte(i_19_);
			boolean bool = (0x1 & i_19_) == 1;
			if(bool) {
				out.writeShort(anInt4709);
				out.writeShort(anInt4797);
				out.writeShort(anInt4815);
				out.writeShort(anInt4821);
				out.writeShort(anInt4682);
				out.writeShort(zoom);
			} else if(aBoolean4865){
				out.writeShort(anInt4709);
				out.writeShort(anInt4797);
				out.writeShort(anInt4842);
				out.writeShort(anInt4815);
				out.writeShort(anInt4821);
				out.writeShort(anInt4682);
				out.writeShort(zoom);
			}
			if ((ihash >> 16) > 1144)
				out.writeBigSmart(animationId);
			else {
				out.writeShort(animationId);
			}
			if (aspectWidthType != 0) {
				out.writeShort(anInt4800);
			}
			if ((aspectHeightType ^ 0xffffffff) != -1) {
				out.writeShort(anInt4849);
			}

		}
		/**
		 * text
		 */
		if(type == ComponentConstants.TEXT){
			if ((ihash >> 16) > 1144)
				out.writeBigSmart(fontId );
			else
				out.writeShort(fontId);
			if (newInt >= 2) {
				if(aBoolean4832)
					out.writeByte(1);
				else
					out.writeByte(0);
			}
			out.writeString(text);
			out.writeByte(anInt4697);
			out.writeByte(textHorizontalAli);
			out.writeByte(textVerticalAli);
			if(shadow)
				out.writeByte(1);
			else
				out.writeByte(0);
			out.writeInt(color);
			out.writeByte(transparency);
			if (newInt >= 0)
				out.writeByte(multiline);
		}
		if(type == ComponentConstants.FIGURE){
			out.writeInt(this.color);
			if(filled)
				out.writeByte(1);
			else
				out.writeByte(0);
			out.writeByte(transparency);
		}
		if(type == ComponentConstants.LINE){
			out.writeByte(lineWidth);
			out.writeInt(color);
			if(lineDirection)
				out.writeByte(1);
			else
				out.writeByte(0);
		}
		out.write24BitInt(optionMask);
		out.writeByte(i_21_);
		if(i_21_ != 0){
			//system.out.println("USED");
			/**
			 * TODO loop shit , almost always 0 so dw
			 */
			for(int i = 0; i < anIntArray4705.length; i++){

				out.writeInt(anIntArray4705[i]);
				out.writeByte(aByteArray4806[i]);
				out.writeByte(aByteArray4733[i]);
			}

		}

		out.writeString(Name);
		if(rightclickOptions != null)
			out.writeByte(rightclickOptions.length);
		else
			out.writeByte(0);
		if(rightclickOptions != null) {
			if(rightclickOptions.length > 0){
				for (int index = 0; index  < rightclickOptions.length ;index++){
					out.writeString(this.rightclickOptions[index] == null ? "" :this.rightclickOptions[index] +"");
				}

			}
		}
		int i_28_ = menuOptionsCount  >> 4;
		if((i_28_ ^ 0xffffffff) < -1){
			out.writeByte(Option);
			System.out.println("Option= "+Option);
			out.writeShort(opCursors[Option]);
		} if ((i_28_ ^ 0xffffffff) < -2) {
			out.writeByte(option);
			out.writeShort(opCursors[option]);
		}
		if(aString4784 == null)
			out.writeString("");
		else
			out.writeString(aString4784);
		out.writeByte(anInt4708);
		out.writeByte(anInt4795);
		out.writeByte(anInt4860);
		out.writeString(aString4786);
		if ((method925(optionMask) ^ 0xffffffff) != -1) {
			out.writeShort(mask);
			out.writeShort(targetOverCursor);
			out.writeShort(targetLeaveCursor);
		}
		if (newInt >= 0) {
			out.writeShort(mouseOverCursor);
		}
		if(newInt >= 0){
			out.writeByte(i_33_);
			for(int i = 0; i < hashInt1.size(); i ++){
				out.writeLong(hashLong1.get(i));
				out.writeInt(hashInt1.get(i));

			}

			out.writeByte(i_37_);
			for(int i = 0; i < hashInt1.size(); i ++){
				out.writeLong(hashLong2.get(i));
				out.writeString(hashString2.get(i));

			}
		}
		encodeScript(onLoadScript,out);
		encodeScript(onMouseHoverScript,out);
		encodeScript(onMouseLeaveScript,out);
		encodeScript(anObjectArray4771,out);
		encodeScript(anObjectArray4768 ,out);
		encodeScript(anObjectArray4807 ,out);
		encodeScript(anObjectArray4742,out);
		encodeScript(anObjectArray4788 ,out);
		encodeScript(anObjectArray4701 ,out);
		encodeScript(anObjectArray4770 ,out);
		if ((newInt ^ 0xffffffff) <= -1) {
			encodeScript(anObjectArray4751,out);
		}
		encodeScript(popupScript,out);
		encodeScript(anObjectArray4774,out);
		encodeScript(anObjectArray4803 ,out);
		encodeScript(anObjectArray4680,out);
		encodeScript(anObjectArray4856 ,out);
		encodeScript(anObjectArray4852 ,out);
		encodeScript(anObjectArray4711,out);
		encodeScript(anObjectArray4753,out);
		encodeScript(anObjectArray4688	,out);
		encodeScript(anObjectArray4775 ,out);


		encodeScripts3(this.configs	,out);
		encodeScripts3(this.anIntArray4833 	,out);
		encodeScripts3(this.anIntArray4789	,out);
		encodeScripts3(this.anIntArray4829	,out);
		encodeScripts3(this.anIntArray4805    ,out);
		byte[] data = new byte[out.getOffset()];
		out.setOffset(0);
		out.getBytes(data, 0, data.length -1);
		return data;

	}

	public int flag;
	public byte newInt;
	private int flag2;
	private int i_19_;
	public int optionMask;
	private int i_21_;
	private byte b_23_;
	private byte b_24_;
	public int Option;
	private int option;
	private int i_33_;
	private int i_37_;
	private int i_39_;
	private int mask;
	public int menuOptionsCount;
	private ArrayList<Integer> hashInt1 = new ArrayList();
	private ArrayList<String> hashString2 = new ArrayList();
	private ArrayList<Long> hashLong1 = new ArrayList<Long>();
	private ArrayList<Long> hashLong2 = new ArrayList();




	@SuppressWarnings("unchecked")
	final void decode(InputStream stream, int id, int interfaceId) {
		newInt = (byte) stream.readUnsignedByte();
		if (newInt == 255) {
			newInt = -1;
		}
		this.componentId = id;
		this.interfaceId = interfaceId;
		type = stream.readUnsignedByte();
		if ((type & 0x80) != 0) {
			type &= 0x7f;
			name = stream.readString();
		}
		contentType = stream.readUnsignedShort(); //mostly 0 still have to found out
		basePositionX = stream.readShort();
		basePositionY = stream.readShort();
		baseWidth = stream.readUnsignedShort();
		baseHeight = stream.readUnsignedShort();
		aspectWidthType = (byte) stream.readByte();
		aspectHeightType = (byte) stream.readByte();
		aspectXType = (byte) stream.readByte();
		aspectYType = (byte) stream.readByte();
		parentId = stream.readUnsignedShort();
		if (parentId != 65535) {
			parentId = parentId + (ihash & ~0xffff);
		} else {
			parentId = -1;
		}
		int flag = stream.readUnsignedByte();
		this.flag  = flag;
		hidden = (0x1 & flag ^ 0xffffffff) != -1;//0 != (flag & 0x1);
		if (newInt >= 0) {
			disableHover = (flag & 0x2 ^ 0xffffffff) != -1;
		}
		/**
		 * container
		 */
		if (type == ComponentConstants.CONTAINER) {
			layerWidth = stream.readUnsignedShort(); //scroll x ?
			layerHeight = stream.readUnsignedShort();	//scroll y ?
			if ((newInt ^ 0xffffffff) > -1) {
				disableHover = stream.readUnsignedByte() == 1;
			}
		}
		/**
		 * sprite
		 */
		if (type == ComponentConstants.SPRITE) {
			spriteId = stream.readInt();
			anInt4728 = stream.readUnsignedShort();
			flag2 = stream.readUnsignedByte();
			repeat_ = (flag2 & 0x1 ^ 0xffffffff) != -1; // repeat ?
			transparency = stream.readUnsignedByte();
			borderThickness = stream.readUnsignedByte();
			anInt4796 = stream.readInt(); //also shadow
			vFlip = stream.readUnsignedByte() == 1; //flip 1
			hFlip = stream.readUnsignedByte() == 1; //flip 2
			color = stream.readInt();
			if ((newInt ^ 0xffffffff) <= -4) {
				clickMask = (stream.readUnsignedByte() ^ 0xffffffff) == -2;
			}
		}
		if (type == 6) {
			if ((ihash >> 16) > 1144)
				modelId = stream.readBigSmart();
			else {
				modelId = stream.readUnsignedShort();
				if ((modelId ^ 0xffffffff) == -65536) {
					modelId = -1;
				}
			}
			i_19_ = stream.readUnsignedByte();
			boolean bool = (0x1 & i_19_) == 1;
			aBoolean4865 = (i_19_ & 0x2 ^ 0xffffffff) == -3;
			if (bool) {
				anInt4709 = stream.readShort();
				anInt4797 = stream.readShort();
				anInt4815 = stream.readUnsignedShort();
				anInt4821 = stream.readUnsignedShort();
				anInt4682 = stream.readUnsignedShort();
				zoom = stream.readUnsignedShort();
			} else if (aBoolean4865) {
				anInt4709 = stream.readShort();
				anInt4797 = stream.readShort();
				anInt4842 = stream.readShort();
				anInt4815 = stream.readUnsignedShort();
				anInt4821 = stream.readUnsignedShort();
				anInt4682 = stream.readUnsignedShort();
				zoom = stream.readShort();
			}
			//zoom = 850;
			//System.out.println(interfaceId+" "+componentId+" values:" + zoom +" "+anInt4682+" "+anInt4821);

			if ((ihash >> 16) > 1144)
				animationId = stream.readBigSmart();
			else {
				animationId = stream.readUnsignedShort();
				if ((animationId ^ 0xffffffff) == -65536) {
					animationId = -1;
				}
			}
			if (aspectWidthType != 0) {
				anInt4800 = stream.readUnsignedShort();
			}
			if ((aspectHeightType ^ 0xffffffff) != -1) {
				anInt4849 = stream.readUnsignedShort();
			}
		}
		/**
		 * text
		 */
		if (type == ComponentConstants.TEXT) {
			if ((ihash >> 16) > 1144)
				fontId = stream.readBigSmart();
			else {
				fontId = stream.readUnsignedShort();
				if ((fontId ^ 0xffffffff) == -65536) {
					fontId = -1;
				}
			}
			if (newInt >= 2) {
				aBoolean4832 = (stream.readUnsignedByte()) == 1;
			}
			text = stream.readString();
			anInt4697 = stream.readUnsignedByte();
			textHorizontalAli = stream.readUnsignedByte();
			textVerticalAli = stream.readUnsignedByte();
			shadow = stream.readUnsignedByte()  == 1;
			color = stream.readInt();
			transparency = stream.readUnsignedByte();
			if (newInt >= 0) {
				multiline = stream.readUnsignedByte();
			}
		}
		if (type == ComponentConstants.FIGURE) {
			color = stream.readInt();
			filled = (stream.readUnsignedByte() ^ 0xffffffff) == -2; //filled ?
			transparency = stream.readUnsignedByte();
		}
		if (type == ComponentConstants.LINE) {
			lineWidth = stream.readUnsignedByte();
			color = stream.readInt();
			lineDirection = stream.readUnsignedByte() == 1;
		}
		optionMask = stream.read24BitInt();
		i_21_ = stream.readUnsignedByte();
		////system.out.println("before if "+i_21_);
		if ((i_21_ ^ 0xffffffff) != -1) {//if (i_21_ != 0) {
			aByteArray4806 = new byte[11];
			aByteArray4733 = new byte[11];
			anIntArray4705 = new int[11];
			for (; i_21_ != 0; i_21_ = stream.readUnsignedByte()) {
				//system.out.println("After for : "+i_21_);
				int i_22_ = (i_21_ >> 4) - 1;
				i_21_ = stream.readUnsignedByte() | i_21_ << 8;
				//system.out.println("second one : "+i_21_);
				i_21_ &= 0xfff;
				if (i_21_ == 4095) {
					i_21_ = -1;
				}
				b_23_ = (byte) stream.readByte();
				if (b_23_ != 0) {
				}
				b_24_ = (byte) stream.readByte();
				//system.out.println(" Index : "+i_22_);
				//system.out.println("anInt4761="+anInt4761);
				if(i_22_ > -1) {
					anIntArray4705[i_22_] = i_21_;
					aByteArray4806[i_22_] = b_23_;
					aByteArray4733[i_22_] = b_24_;
				}
			}
		}
		Name = stream.readString();

		int  options_length = stream.readUnsignedByte();
		menuOptionsCount =  options_length & 0xf ;
		int menuCursorMask = options_length >> 4;
		//System.out.println(this.interfaceId+" "+this.componentId+" =  menuCursorMask: "+menuCursorMask+ " menuOptionsCount: "+menuOptionsCount);
		if (options_length > 0) {
			rightclickOptions = new String[options_length];
			for (int index = 0;index < rightclickOptions.length ;index++){
				rightclickOptions[index] = stream.readString();
			}
		}
		if (menuCursorMask > 0) {
			Option = stream.readUnsignedByte();
			opCursors = new int[Option - -1];
			for (int i_30_ = 0; (i_30_ ^ 0xffffffff) > (opCursors.length ^ 0xffffffff); i_30_++){
				opCursors[i_30_] = -1;

			}
			opCursors[Option] = stream.readUnsignedShort();

		}

		if (menuCursorMask > 1) {
			option = stream.readUnsignedByte();
			opCursors[option] = stream.readUnsignedShort();
		}
		aString4784 = stream.readString();
		if (aString4784.equals("")) {
			aString4784 = null;
		}
		anInt4708 = stream.readUnsignedByte();
		anInt4795 = stream.readUnsignedByte();
		anInt4860 = stream.readUnsignedByte();
		aString4786 = stream.readString();
		mask = -1;
		if ((method925(optionMask) ^ 0xffffffff) != -1) {
			mask = stream.readUnsignedShort();
			if (mask == 65535) {
				mask = -1;
			}
			targetOverCursor = stream.readUnsignedShort();
			//system.out.println("anInt4698="+anInt4698);
			if ((targetOverCursor == 65535)) {
				targetOverCursor = -1;
			}
			targetLeaveCursor = stream.readUnsignedShort();
			//system.out.println("anInt4839="+anInt4839);
			if (targetLeaveCursor == 65535) {
				targetLeaveCursor = -1;
			}
		}
		if (newInt >= 0) {
			mouseOverCursor = stream.readUnsignedShort();
			//system.out.println("anInt4761="+anInt4761);
			if (mouseOverCursor == 65535) {
				mouseOverCursor = -1;
			}
		}
		activeProperties = new IComponentSettings(optionMask, mask);
		if (newInt >= 0) {
			i_33_ = stream.readUnsignedByte();
			for (int i_34_ = 0; i_33_ > i_34_; i_34_++) {
				int i_35_ = stream.read24BitInt();
				int i_36_ = stream.readInt();
				aHashTable4823.put((long) i_35_, i_36_);
				hashInt1.add(i_36_);
				//system.out.println("_i_36"+i_36_);
				hashLong1.add((long)i_35_);

			}
			i_37_ = stream.readUnsignedByte();
			for (int i_38_ = 0; i_38_ < i_37_; i_38_++) {
				i_39_ = stream.read24BitInt();
				String string = stream.readJagString();
				aHashTable4823.put((long) i_39_, string);
				hashLong2.add((long)i_39_);
				System.out.println(string);
				hashString2.add(string);
			}
		}
		onLoadScript = decodeScript(stream);
		onMouseHoverScript = decodeScript(stream);
		onMouseLeaveScript = decodeScript(stream);
		anObjectArray4771 = decodeScript(stream);
		anObjectArray4768 = decodeScript(stream);
		anObjectArray4807 = decodeScript(stream);
		anObjectArray4742 = decodeScript(stream);
		anObjectArray4788 = decodeScript(stream);
		anObjectArray4701 = decodeScript(stream);
		anObjectArray4770 = decodeScript(stream);
		if(anObjectArray4770 != null){

		}
		if ((newInt ^ 0xffffffff) <= -1) {
			anObjectArray4751 = decodeScript(stream);
		}
		/**
		 * client script hover text
		 */
		popupScript = decodeScript(stream);
		/*
		 * end
		 */
		anObjectArray4774 = decodeScript(stream);
		anObjectArray4803 = decodeScript(stream);
		anObjectArray4680 = decodeScript(stream);
		anObjectArray4856 = decodeScript(stream);
		anObjectArray4852 = decodeScript(stream);
		anObjectArray4711 = decodeScript(stream);
		anObjectArray4753 = decodeScript(stream);
		anObjectArray4688 = decodeScript(stream);
		anObjectArray4775 = decodeScript(stream);
		configs = decodeScripts3(stream);
		anIntArray4833 = decodeScripts3(stream);
		anIntArray4789 = decodeScripts3(stream);
		anIntArray4829 = decodeScripts3(stream);
		anIntArray4805 = decodeScripts3(stream);
	}

	private final Object[] decodeScript(InputStream buffer) {

		int length = buffer.readUnsignedByte();
		if (length == 0) {
			return null;
		}
		Object[] objects = new Object[length];
		for (int index = 0; length > index; index++) {
			int type = buffer.readUnsignedByte();
			if (type == ComponentConstants.CONTAINER) {
				int int1 = new Integer(buffer.readInt());
				objects[index] = int1;
			} else if ((type ^ 0xffffffff) == -2) {
				objects[index] = buffer.readString();
			}
		}
		//System.out.println("End script");
		return objects;
	}

	private void encodeScript(Object[] obj,OutputStream out){
		int length;
		if(obj == null)
			length = 0;
		else
			length = obj.length;
		out.writeByte(length);
		if(obj == null)
			return;
		for(int index = 0; index < length; index++){
			Object arg = obj[index];
			if(arg instanceof String){
				out.writeByte(1);
				out.writeString((String)arg);
			} else if(arg instanceof Integer){
				out.writeByte(0);
				out.writeInt((int)arg);
			}
		}
	}

	/**
	 * encodes method 4150 custom method
	 * @param arr
	 * @param out
	 */
	private void encodeScripts3(int[] arr, OutputStream out){
		int length;
		if(arr == null)
			length = 0;
		else
			length = arr.length;
		out.writeByte(length);
		for(int index = 0; index < length; index++){
			out.writeInt(arr[index]);
		}
	}

	private final int[] decodeScripts3(InputStream buffer) {
		int length = buffer.readUnsignedByte();
		if (length == 0) {
			return null;
		}
		int[] is = new int[length];
		for (int index = 0; index < length; index++){
			is[index] = buffer.readInt();
		}
		return is;
	}
	/**
	 * archive ?
	 * @param i
	 * @return
	 */
	static final int method925(int i) {
		return (i & 0x3fda8) >> 11;
	}
	/**
	 * returns all the childeren of a sprite
	 * @param interfaceId
	 * @param hash
	 * @return
	 */
	public static  ArrayList<ComponentDefinition> getChildsByParent(int interfaceId, int hash){
		ComponentDefinition[] allComponents = getInterface(interfaceId);
		ArrayList<ComponentDefinition> foundChilderen = new ArrayList<ComponentDefinition>();
		for (ComponentDefinition component : allComponents){
			if(component == null) continue;
			if(hash == component.parentId)
				foundChilderen.add(component);
		}
		return foundChilderen;
	}

	public static boolean hasChilds(int interfaceId, int parentHash){
		ComponentDefinition[] list = ComponentDefinition.getInterface(interfaceId);
		for(ComponentDefinition c : list){
			if(c == null)
				continue;
			if(c.parentId == parentHash)
				return true;
		}
		return false;
	}
	/**
	 * returns all the containers of a single interface
	 * @param interfaceId
	 * @return
	 */
	public static ArrayList<ComponentDefinition> getInterfaceContainers(int interfaceId){
		ComponentDefinition[] possibleParents = getInterface(interfaceId);
		ArrayList<ComponentDefinition> containers = new ArrayList<ComponentDefinition>();
		if(possibleParents == null)
			return null;
		for(ComponentDefinition component : possibleParents){
			if(component == null)
				continue;
			if(component.type == ComponentConstants.CONTAINER && component.hasChilds(interfaceId, component.ihash)){
				////system.out.println("Container id: "+component.componentId);
				containers.add(component);
			}
		}

		return containers;
	}

	public static int getX(ComponentDefinition c, int inter){
		if(c.parentId == -1)
			return c.positionX;
		ComponentDefinition parent = InterfaceUtils.getParent(c.parentId);
		int positionX = c.positionX;
		while(parent != null) {
			ComponentPosition.setValues(parent);
			positionX += parent.positionX;
			parent = InterfaceUtils.getParent(parent.parentId);
		}
		return positionX;
	}

	public static int getY(ComponentDefinition c, int inter){
		if(c.parentId == -1)
			return c.positionY;
		ComponentDefinition parent = InterfaceUtils.getParent(c.parentId);
		int positionY = c.positionY;
		while(parent != null) {
			ComponentPosition.setValues(parent);
			positionY += parent.positionY;
			parent = InterfaceUtils.getParent(parent.parentId);
		}

		return positionY;
	}




	/**
	 * constructor
	 */
	public ComponentDefinition() {
		targetOverCursor = -1;
		anInt4682 = 0;
		lineWidth = 1;
		anInt4728 = 0;
		fontId = -1;
		filled = false;
		baseWidth = 0;
		multiline = 0;
		aspectWidthType = (byte) 0;
		mouseOverCursor = -1;
		Name = "";
		aspectHeightType = (byte) 0;
		clickMask = true;
		transparency = 0;
		color = 0;
		animationId = -1;
		anInt4795 = 0;
		anInt4796 = 0;
		borderThickness = 0;
		aString4786 = "";
		anInt4697 = 0;
		ihash = -1;
		anInt4821 = 0;
		anInt4815 = 0;
		spriteId = -1;
		lineDirection = false;
		aspectXType = (byte) 0;
		anInt4708 = 0;
		zoom = 100;
		layerHeight = 0;
		shadow = false;
		textVerticalAli = 0;
		text = "";
		aBoolean4832 = true;
		layerWidth = 0;
		anInt4800 = 0;
		width2 = 0;
		textHorizontalAli = 0;
		contentType = 0;
		baseHeight = 0;
		anInt4849 = 0;
		parentId = -1;
		disableHover = false;
		hidden = false;
		basePositionX = 0;
		repeat_ = false;
		anInt4797 = 0;
		anInt4860 = 0;// Class339_Sub4.anInt8679;
		aspectYType = (byte) 0;
		basePositionY = 0;
		anInt4842 = 0;
		targetLeaveCursor = -1;
	}



}
