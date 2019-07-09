package com.rs.cache.loaders;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.alex.store.Store;
import com.rs.cache.Cache;

@SuppressWarnings("unused")
public class ObjectDefinitions  implements Cloneable{


	public static final ConcurrentHashMap<Integer, ObjectDefinitions> objectDefinitions = new ConcurrentHashMap<Integer, ObjectDefinitions>();

	public static void clearObjectDefinitions() {
		objectDefinitions.clear();
	}

	public static int getArchiveId(int i_0_) {
		return i_0_ >>> -1135990488;
	}
	
	public static final int getSize(Store store) {
		int lastArchiveId = store.getIndexes()[16].getLastArchiveId();
		return lastArchiveId * 256
				+ store.getIndexes()[16].getValidFilesCount(lastArchiveId);
	}


	public static ObjectDefinitions getObjectDefinitions(int id) {
		ObjectDefinitions def = objectDefinitions.get(id);
		if (def == null) {
			def = new ObjectDefinitions();
			def.id = id;
			byte[] data = Cache.STORE.getIndexes()[16].getFile(
					getArchiveId(id), id & 0xff);
			if (data == null) {
				// System.out.println("Failed loading Object " + id + ".");
			} else
				def.readValueLoop(new InputStream(data));
			def.method3287();


			objectDefinitions.put(id, def);
		}
		return def;
	}

	public static void main(String[] args) throws IOException {
		Cache.init();
		final ObjectDefinitions defs = getObjectDefinitions(92256);//92256
		if (defs == null) {
			System.err.println("Defs equal to null!");
			return;
		}
		System.out.println("Name: "+defs.name);
		if (defs.toObjectIds != null) {
			System.out.println("To object ids:");
			for (final int i : defs.toObjectIds)
				System.out.println("TOI: "+i);
		}
		if (defs.anIntArray3859 != null) {
			System.out.println("int array 0");
			for (final int i : defs.anIntArray3859)
				System.out.println("int array 0: "+i);
		}
		if (defs.anIntArray3869 != null) {
			System.out.println("int array 1");
			for (final int i : defs.anIntArray3869)
				System.out.println("int array 1: "+i);
		}
		if (defs.anIntArray3908 != null) {
			System.out.println("int array 2");
			for (final int i : defs.anIntArray3908)
				System.out.println("int array 2: "+i);
		}
		if (defs.anIntArray4534 != null) {
			System.out.println("int array 3");
			for (final int i : defs.anIntArray4534)
				System.out.println("int array 3: "+i);
		}
		
	}

	public short[] originalColors;
	int[] toObjectIds;
	static int anInt3832;
	int[] animations = null;
	public int anInt3834;
	int anInt3835;
	static int anInt3836;
	public byte aByte3837;
	int anInt3838 = -1;
	boolean aBoolean3839;
	public int anInt3840;
	public int anInt3841;
	static int anInt3842;
	static int anInt3843;
	int anInt3844;
	boolean aBoolean3845;
	static int anInt3846;
	public byte aByte3847;
	public byte aByte3849;
	int anInt3850;
	int anInt3851;
	public boolean secondBool;
	public boolean aBoolean3853;
	int anInt3855;
	public boolean ignoreClipOnAlternativeRoute;
	int anInt3857;
	public byte[] aByteArray3858;
	int[] anIntArray3859;
	int anInt3860;
	public String[] options;
	public int configFileId;
	public short[] modifiedColors;
	int anInt3865;
	boolean aBoolean3866;
	boolean aBoolean3867;
	public boolean projectileCliped;
	public int[] anIntArray3869;
	boolean aBoolean3870;
	public int sizeY;
	boolean aBoolean3872;
	boolean aBoolean3873;
	public int thirdInt;
	public int anInt3875;
	public int objectAnimation;
	public int anInt3877;
	public int anInt3878;
	public int clipType;
	public int anInt3881;
	public int anInt3882;
	public int anInt3883;
	Object loader;
	public int anInt3889;
	public int sizeX;
	public boolean aBoolean3891;
	public int anInt3892;
	public int secondInt;
	boolean aBoolean3894;
	boolean aBoolean3895;
	int anInt3896;
	public int configId;
	public byte[] possibleTypes;
	int anInt3900;
	public String name;
	public int anInt3902;
	int anInt3904;
	int anInt3905;
	boolean aBoolean3906;
	int[] anIntArray3908;
	public byte aByte3912;
	int anInt3913;
	public byte aByte3914;
	public int anInt3915;
	public int[][] modelIds;
	public int anInt3917;
	/**
	 * Object anim shit 1
	 */
	public short[] destTextures;
	/**
	 * Object anim shit 2
	 */
	public short[] originalTextures;
	int anInt3921;

	public HashMap<Integer, Object> parameters;

	boolean aBoolean3923;

	boolean aBoolean3924;

	int anInt3925;

	public int id;

	public int[] anIntArray4534;

	public byte[] unknownArray4;

	public byte[] unknownArray3;

	public int cflag;
	public boolean notCliped;

	public ObjectDefinitions() {
		anInt3835 = -1;
		anInt3860 = -1;
		configFileId = -1;
		aBoolean3866 = false;
		anInt3851 = -1;
		anInt3865 = 255;
		aBoolean3845 = false;
		aBoolean3867 = false;
		anInt3850 = 0;
		anInt3844 = -1;
		anInt3881 = 0;
		anInt3857 = -1;
		aBoolean3872 = true;
		anInt3882 = -1;
		anInt3834 = 0;
		options = new String[5];
		anInt3875 = 0;
		aBoolean3839 = false;
		anIntArray3869 = null;
		sizeY = 1;
		thirdInt = -1;
		anInt3883 = 0;
		aBoolean3895 = true;
		anInt3840 = 0;
		aBoolean3870 = false;
		anInt3889 = 0;
		aBoolean3853 = true;
		secondBool = false;
		clipType = 2;
		projectileCliped = true;
		ignoreClipOnAlternativeRoute = false;
		anInt3855 = -1;
		anInt3878 = 0;
		anInt3904 = 0;
		sizeX = 1;
		objectAnimation = -1;
		aBoolean3891 = false;
		anInt3905 = 0;
		name = "null";
		anInt3913 = -1;
		aBoolean3906 = false;
		aBoolean3873 = false;
		aByte3914 = (byte) 0;
		anInt3915 = 0;
		anInt3900 = 0;
		secondInt = -1;
		aBoolean3894 = false;
		aByte3912 = (byte) 0;
		anInt3921 = 0;
		anInt3902 = 128;
		configId = -1;
		anInt3877 = 0;
		anInt3925 = 0;
		anInt3892 = 64;
		aBoolean3923 = false;
		aBoolean3924 = false;
		anInt3841 = 128;
		anInt3917 = 128;
	}

	public boolean containsOption(int i, String option) {
		if (options == null || options[i] == null || options.length <= i)
			return false;
		return options[i].equals(option);
	}

	public boolean containsOption(String o) {
		if (options == null)
			return false;
		for (String option : options) {
			if (option == null)
				continue;
			if (option.equalsIgnoreCase(o))
				return true;
		}
		return false;
	}

	public int getAccessBlockFlag() {
		return cflag;
	}

	public static void configByFile(int objectId) {
		ObjectDefinitions defs = ObjectDefinitions
				.getObjectDefinitions(objectId);
	//	player.getPackets().sendGameMessage("ConfigByFile ID is: " + defs.configFileId+", Config ID is: "+defs.configId, true);
	}

	public static void objectAnimationId( int objectId) {
		ObjectDefinitions defs = ObjectDefinitions
				.getObjectDefinitions(objectId);
		//player.getPackets().sendGameMessage("Object animation id is: "+defs.objectAnimation, true);
	}

	public int getClipType() {
		return clipType;
	}

	public String getFirstOption() {
		if (options == null || options.length < 1)
			return "";
		return options[0];
	}
	
	public String[] getOptions(){
		return options;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getOption(int option) {
		if (options == null || options.length < option || option == 0)
			return "";
		return options[option - 1];
	}

	public String getSecondOption() {
		if (options == null || options.length < 2)
			return "";
		return options[1];
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public String getThirdOption() {
		if (options == null || options.length < 3)
			return "";
		return options[2];
	}

	public Object getValue(Field field) throws Throwable {
		field.setAccessible(true);
		Class<?> type = field.getType();
		if (type == int[][].class) {
			return Arrays.toString((int[][]) field.get(this));
		} else if (type == int[].class) {
			return Arrays.toString((int[]) field.get(this));
		} else if (type == byte[].class) {
			return Arrays.toString((byte[]) field.get(this));
		} else if (type == short[].class) {
			return Arrays.toString((short[]) field.get(this));
		} else if (type == double[].class) {
			return Arrays.toString((double[]) field.get(this));
		} else if (type == float[].class) {
			return Arrays.toString((float[]) field.get(this));
		} else if (type == Object[].class) {
			return Arrays.toString((Object[]) field.get(this));
		}
		return field.get(this);
	}

	public boolean isProjectileCliped() {
		return projectileCliped;
	}

	final void method3287() {
		if (secondInt == -1) {
			secondInt = 0;
			if (possibleTypes != null && possibleTypes.length == 1
					&& possibleTypes[0] == 10)
				secondInt = 1;
			for (int i_13_ = 0; i_13_ < 5; i_13_++) {
				if (options[i_13_] != null) {
					secondInt = 1;
					break;
				}
			}
		}
		if (anInt3855 == -1)
			anInt3855 = clipType != 0 ? 1 : 0;
	}

	/**
	 * Prints all fields in this class.
	 */
	public void printFields() {
		for (Field field : getClass().getDeclaredFields()) {
			if ((field.getModifiers() & 8) != 0) {
				continue;
			}
			try {
				System.out.println(field.getName() + ": " + getValue(field));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		System.out.println("-- end of " + getClass().getSimpleName()
				+ " fields --");
	}

	public void readValueLoop(InputStream stream) {
		for (;;) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0) {
				// System.out.println("Remaining: "+stream.getRemaining());
				break;
			}
			readValues(stream, opcode);
		}
	}
	
	public void readValues(InputStream stream, int opcode) {
		try {
		if (opcode != 1){// && opcode != 5) {//TODO Danke clem
			if (opcode != 2) {
				if (opcode != 14) {
					if (opcode != 15) {
						if (opcode == 17) { // nocliped
							projectileCliped = false;
							clipType = 0;
						} else if (opcode != 18) {
							if (opcode == 19)
								secondInt = stream.readUnsignedByte();
							else if (opcode == 21)
								aByte3912 = (byte) 1;
							else if (opcode != 22) {
								if (opcode != 23) {
									if (opcode != 24) {
										if (opcode == 27) // cliped, no idea
											// diff between 2
											// and 1
											clipType = 1;
										else if (opcode == 28)
											anInt3892 = (stream.readUnsignedByte() << 2);
										else if (opcode != 29) {
											if (opcode != 39) {
												if (opcode < 30 || opcode >= 35) {
													if (opcode == 40) {
														int i_53_ = (stream.readUnsignedByte());
														originalColors = new short[i_53_];
														modifiedColors = new short[i_53_];
														for (int i_54_ = 0; i_53_ > i_54_; i_54_++) {
															originalColors[i_54_] = (short) (stream.readUnsignedShort());
															modifiedColors[i_54_] = (short) (stream.readUnsignedShort());
														}
													} else if (44 == opcode) {
														int i_86_ = (short) stream.readUnsignedShort();
														int i_87_ = 0;
														for (int i_88_ = i_86_; i_88_ > 0; i_88_ >>= 1)
															i_87_++;
														unknownArray3 = new byte[i_87_];
														byte i_89_ = 0;
														for (int i_90_ = 0; i_90_ < i_87_; i_90_++) {
															if ((i_86_ & 1 << i_90_) > 0) {
																unknownArray3[i_90_] = i_89_;
																i_89_++;
															} else
																unknownArray3[i_90_] = (byte) -1;
														}
													} else if (opcode == 45) {
														int i_91_ = (short) stream.readUnsignedShort();
														int i_92_ = 0;
														for (int i_93_ = i_91_; i_93_ > 0; i_93_ >>= 1)
															i_92_++;
														unknownArray4 = new byte[i_92_];
														byte i_94_ = 0;
														for (int i_95_ = 0; i_95_ < i_92_; i_95_++) {
															if ((i_91_ & 1 << i_95_) > 0) {
																unknownArray4[i_95_] = i_94_;
																i_94_++;
															} else
																unknownArray4[i_95_] = (byte) -1;
														}
													} else if (opcode != 41) { // object
														// anim
														if (opcode != 42) {
															if (opcode != 62) {
																if (opcode != 64) {
																	if (opcode == 65)
																		anInt3902 = stream.readUnsignedShort();
																	else if (opcode != 66) {
																		if (opcode != 67) {
																			if (opcode == 69)
																				cflag = stream.readUnsignedByte();
																			else if (opcode != 70) {
																				if (opcode == 71)
																					anInt3889 = stream.readShort() << 2;
																				else if (opcode != 72) {
																					if (opcode == 73)
																						secondBool = true;
																					else if (opcode == 74)
																						ignoreClipOnAlternativeRoute = true;
																					else if (opcode != 75) {
																						if (opcode != 77 && opcode != 92) {
																							if (opcode == 78) {
																								anInt3860 = stream.readUnsignedShort();
																								anInt3904 = stream.readUnsignedByte();
																							} else if (opcode != 79) {
																								if (opcode == 81) {
																									aByte3912 = (byte) 2;
																									anInt3882 = 256 * stream.readUnsignedByte();
																								} else if (opcode != 82) {
																									if (opcode == 88)
																										aBoolean3853 = false;
																									else if (opcode != 89) {
																										if (opcode == 90)
																											aBoolean3870 = true;
																										else if (opcode != 91) {
																											if (opcode != 93) {
																												if (opcode == 94)
																													aByte3912 = (byte) 4;
																												else if (opcode != 95) {
																													if (opcode != 96) {
																														if (opcode == 97)
																															aBoolean3866 = true;
																														else if (opcode == 98)
																															aBoolean3923 = true;
																														else if (opcode == 99) {
																															anInt3857 = stream.readUnsignedByte();
																															anInt3835 = stream.readUnsignedShort();
																														} else if (opcode == 100) {
																															anInt3844 = stream.readUnsignedByte();
																															anInt3913 = stream.readUnsignedShort();
																														} else if (opcode != 101) {
																															if (opcode == 102)
																																stream.readUnsignedShort();
																															else if (opcode == 103)
																																thirdInt = 0;
																															else if (opcode != 104) {
																																if (opcode == 105)
																																	aBoolean3906 = true;
																																else if (opcode == 106) {
																																	int i_55_ = stream.readUnsignedByte();
																																	anIntArray3869 = new int[i_55_];
																																	animations = new int[i_55_];
																																	for (int i_56_ = 0; i_56_ < i_55_; i_56_++) {
																																		animations[i_56_] = stream.readBigSmart();
																																		int i_57_ = stream.readUnsignedByte();
																																		anIntArray3869[i_56_] = i_57_;
																																		anInt3881 += i_57_;
																																	}
																																} else if (opcode == 107)
																																	stream.readUnsignedShort();
																																else if (opcode >= 150 && opcode < 155) {
																																	options[opcode + -150] = stream.readString();
																																} else if (opcode != 160) {
																																	if (opcode == 162) {
																																		aByte3912 = (byte) 3;
																																		anInt3882 = stream.readInt();
																																	} else if (opcode == 163) {
																																		aByte3847 = (byte) stream.readByte();
																																		aByte3849 = (byte) stream.readByte();
																																		aByte3837 = (byte) stream.readByte();
																																		aByte3914 = (byte) stream.readByte();
																																	} else if (opcode != 164) {
																																		if (opcode != 165) {
																																			if (opcode != 166) {
																																				if (opcode == 167)
																																					anInt3921 = stream.readUnsignedShort();
																																				else if (opcode != 168) {
																																					if (opcode == 169) {
																																						aBoolean3845 = true;
																																						// added
																																						// opcode
																																					} else if (opcode == 170) {
																																						int anInt3383 = stream.readUnsignedSmart();
																																						// added
																																						// opcode
																																					} else if (opcode == 171) {
																																						int anInt3362 = stream.readUnsignedSmart();
																																						// added
																																						// opcode
																																					} else if (opcode == 173) {
																																						int anInt3302 = stream.readUnsignedShort();
																																						int anInt3336 = stream.readUnsignedShort();
																																						// added
																																						// opcode
																																					} else if (opcode == 177) {
																																						boolean ub = true;
																																						// added
																																						// opcode
																																					} else if (opcode == 178) {
																																						int db = stream.readUnsignedByte();
																																					} else if (186 == opcode) {//Unknown
																																						stream.readUnsignedByte();
																																					} else if (opcode == 189) {
																																						boolean bloom = true;
																																					} else if (opcode >= 190 && opcode < 196) {
																																						if (anIntArray4534 == null) {
																																							anIntArray4534 = new int[6];
																																							Arrays.fill(anIntArray4534, -1);
																																						}
																																						anIntArray4534[opcode - 190] = stream.readUnsignedShort();
																																					} else if (196 == opcode) {//Unknown
																																						stream.readUnsignedByte();
																																					} else if (opcode == 197) {//Unknown
																																						stream.readUnsignedByte();
																																					} else if (198 == opcode) { //empty in rs dont ask me why
																																					} else if (199 == opcode) { //empty in rs dont ask me why
																																					}else  if (200 == opcode) {//Unknown
																																				    	boolean aBool6875 = true;
																																				    } else if (opcode == 201) {//Unknown
																																				    	stream.readUnsignedSmart();
																																				    	stream.readUnsignedSmart();
																																				    	stream.readUnsignedSmart();
																																						stream.readUnsignedSmart();
																																						stream.readUnsignedSmart();
																																						stream.readUnsignedSmart();
																																				    	//TODO come here
																																						//Khaled above; Clem below
																																				    	/*stream.readIdk();
																																				    	stream.readIdk();
																																				    	stream.readIdk();
																																				    	stream.readIdk();
																																				    	stream.readIdk();
																																				    	stream.readIdk();*/
																																					} else if (opcode == 249) {
																																						int length = stream.readUnsignedByte();
																																						if (parameters == null)
																																							parameters = new HashMap<Integer, Object>(length);
																																						for (int i_60_ = 0; i_60_ < length; i_60_++) {
																																							boolean bool = stream.readUnsignedByte() == 1;
																																							int i_61_ = stream.read24BitInt();
																																							if (!bool)
																																								parameters.put(i_61_, stream.readInt());
																																							else
																																								parameters.put(i_61_, stream.readString());

																																						}
																																					}
																																				} else
																																					aBoolean3894 = true;
																																			} else
																																				anInt3877 = stream.readShort();
																																		} else
																																			anInt3875 = stream.readShort();
																																	} else
																																		anInt3834 = stream.readShort();
																																} else {
																																	int i_62_ = stream.readUnsignedByte();
																																	anIntArray3908 = new int[i_62_];
																																	for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
																																		anIntArray3908[i_63_] = stream.readUnsignedShort();
																																}
																															} else
																																anInt3865 = stream.readUnsignedByte();
																														} else
																															anInt3850 = stream.readUnsignedByte();
																													} else
																														aBoolean3924 = true;
																												} else {
																													aByte3912 = (byte) 5;
																													anInt3882 = stream.readShort();
																												}
																											} else {
																												aByte3912 = (byte) 3;
																												anInt3882 = stream.readUnsignedShort();
																											}
																										} else
																											aBoolean3873 = true;
																									} else
																										aBoolean3895 = false;
																								} else
																									aBoolean3891 = true;
																							} else {
																								anInt3900 = stream.readUnsignedShort();
																								anInt3905 = stream.readUnsignedShort();
																								anInt3904 = stream.readUnsignedByte();
																								int i_64_ = stream.readUnsignedByte();
																								anIntArray3859 = new int[i_64_];
																								for (int i_65_ = 0; i_65_ < i_64_; i_65_++)
																									anIntArray3859[i_65_] = stream.readUnsignedShort();
																							}
																						} else {
																							configFileId = stream.readUnsignedShort();
																							if (configFileId == 65535)
																								configFileId = -1;
																							configId = stream.readUnsignedShort();
																							if (configId == 65535)
																								configId = -1;
																							int i_66_ = -1;
																							if (opcode == 92) {
																								i_66_ = stream.readBigSmart();
																							}
																							int i_67_ = stream.readUnsignedByte();
																							toObjectIds = new int[i_67_ - -2];
																							for (int i_68_ = 0; i_67_ >= i_68_; i_68_++) {
																								toObjectIds[i_68_] = stream.readBigSmart();
																							}
																							toObjectIds[i_67_ + 1] = i_66_;
																						}
																					} else
																						anInt3855 = stream.readUnsignedByte();
																				} else
																					anInt3915 = stream.readShort() << 2;
																			} else
																				anInt3883 = stream.readShort() << 2;
																		} else
																			anInt3917 = stream.readUnsignedShort();
																	} else
																		anInt3841 = stream.readUnsignedShort();
																} else
																	// 64
																	aBoolean3872 = false;
															} else
																aBoolean3839 = true;
														} else {
															int i_69_ = (stream.readUnsignedByte());
															aByteArray3858 = (new byte[i_69_]);
															for (int i_70_ = 0; i_70_ < i_69_; i_70_++)
																aByteArray3858[i_70_] = (byte) (stream.readByte());
														}
													} else { // object anim?
														int i_71_ = (stream.readUnsignedByte());
														originalTextures = new short[i_71_];
														destTextures = new short[i_71_];
														for (int i_72_ = 0; i_71_ > i_72_; i_72_++) {
															originalTextures[i_72_] = (short) (stream.readUnsignedShort());
															destTextures[i_72_] = (short) (stream.readUnsignedShort());
														}
													}
												} else {
													options[-30 + opcode] = (stream.readString());
												}
											} else
												// 39
												anInt3840 = (stream.readByte() * 5);
										} else {// 29
											anInt3878 = stream.readByte();
										}
									} else {
										objectAnimation = stream.readBigSmart();
									}
								} else
									thirdInt = 1;
							} else
								aBoolean3867 = true;
						} else
							projectileCliped = false;
					} else
						// 15
						sizeY = stream.readUnsignedByte();
				} else
					// 14
					sizeX = stream.readUnsignedByte();
			} else {
				name = stream.readString(); //doens't work, do it client or cache sided
				switch(id){
				case 42010: name = "Oblisk of the Gods";
				break;
				}
			}
			} else {
			boolean aBoolean1162 = false;
			if (opcode == 5 && aBoolean1162)
				skipReadModelIds(stream);
			int i_73_ = stream.readUnsignedByte();
			modelIds = new int[i_73_][];
			possibleTypes = new byte[i_73_];
			for (int index = 0; index < i_73_; index++) {
				possibleTypes[index] = (byte) stream.readByte();
				int ids_length = stream.readUnsignedByte();
				modelIds[index] = new int[ids_length];
				for (int id_index = 0; ids_length > id_index; id_index++) {
					final int value = stream.readBigSmart();
					//System.out.println("Index: "+index+", Id_index: "+id_index+", Value: "+value);
					modelIds[index][id_index] = value;
				}
				
			}
			if (opcode == 5 && !aBoolean1162)
				skipReadModelIds(stream);
		}
		} catch(IndexOutOfBoundsException e) {
			System.err.println("INDEX WAS OUT OF BOUNDS IN OBJECT DEFINITIONS FOR OPCODE: "+opcode);
		}
	}

	public void skipReadModelIds(InputStream stream) {
		int length = stream.readUnsignedByte();
		for (int index = 0; index < length; index++) {
			stream.skip(1);
			int length2 = stream.readUnsignedByte();
			for (int i = 0; i < length2; i++)
				stream.readBigSmart();
		}
	}

	public String getName() {
		return name;
	}

	public int getArchiveId() {
		return id >>> -1135990488;
	}

	public byte[] encode() {
		OutputStream out = new OutputStream();

		if (modelIds != null) {
			out.writeByte(1);
			out.writeByte(modelIds.length);
			for (int i = 0; i < modelIds.length; i++) {
				out.writeByte(possibleTypes[i]);
				int[] models = modelIds[i];
				out.writeByte(models.length);
				for (int index = 0; index < models.length; index++)
					out.writeBigSmart(models[index]);
			}
		}

		if (name != null) {
			out.writeByte(2);
			out.writeString(name);
		}

		out.writeByte(14);
		out.writeByte(sizeX);

		out.writeByte(15);
		out.writeByte(sizeY);

		// if (clip_type == 0)
		// out.write_byte(17);

		// if (!projectile_clipped)
		// out.write_byte(18);

		// out.write_byte(19);
		// out.write_byte(second_int);

		if (aByte3912 == 1)
			out.writeByte(21);

		// if (aBoolean3867)
		// out.write_byte(22);

		// if (third_int == 1)
		// out.write_byte(23);

		out.writeByte(24);
		out.writeBigSmart(objectAnimation);

		if (clipType == 1)
			out.writeByte(27);

		// out.write_byte(28);
		// out.write_byte(anInt3892 >> 2);

		// out.write_byte(29);
		// out.write_byte(anInt3878);

		for (int i = 0; i < 5; i++) {
			if (options[i] == null)
				continue;
			out.writeByte(30 + i);
			out.writeString(options[i]);
		}

		// out.write_byte(39);
		// out.write_byte(anInt3840 / 5);

		if (modifiedColors != null) {
			out.writeByte(40);
			out.writeByte(modifiedColors.length);
			for (int i = 0; i < modifiedColors.length; i++) {
				out.writeShort(originalColors[i]);
				out.writeShort(modifiedColors[i]);
			}
		}

		/*if (modifiedTextures != null) {
			out.writeByte(41);
			out.writeByte(modifiedTextures.length);
			for (int i = 0; i < modifiedTextures.length; i++) {
				out.writeShort(originalTextures[i]);
				out.writeShort(modifiedTextures[i]);
			}
		}*/

		// if (aByteArray3858 != null) {
		// out.write_byte(42);
		// for (int i = 0; i < aByteArray3858.length; i++)
		// out.write_byte(aByteArray3858[i]);
		// }


		// if (aBoolean3839)
		// out.write_byte(62);

		// if (!aBoolean3872)
		// out.write_byte(64);

		// out.write_byte(65);
		// out.write_short(anInt3902);

		// out.write_byte(66);
		// out.write_short(anInt3841);

		// out.write_byte(67);
		// out.write_short(anInt3917);

		// if(cflag != 0) {
		// out.write_byte(69);
		// out.write_byte(cflag);
		// }

		// if (anInt3883 != 0) {
		// out.write_byte(70);
		// out.write_short(anInt3883 >> 2);
		// }

		// if (anInt3889 != 0) {
		// out.write_byte(71);
		// out.write_short(anInt3889 >> 2);
		// }
		//
		// if (anInt3915 != 0) {
		// out.write_byte(72);
		// out.write_short(anInt3915 >> 2);
		// }
		//
		// if (secondBool)
		// out.write_byte(73);
		//
		// if (ignoreClipOnAlternativeRoute)
		// out.write_byte(74);

		out.writeByte(75);
		out.writeByte(anInt3855);

		// if(switch_ids != null) {
		// boolean extra = switch_ids[switch_ids.length - 2] != 0;
		// out.write_byte(extra ? 92 : 77);
		// out.write_short(configFileId);
		// out.write_short(configId);
		// if(extra)
		// out.write_big_smart(switch_ids[switch_ids.length - 2]);
		// for(int i = 0; i < switch_ids.length - 2; i++)
		// out.write_big_smart(switch_ids[i]);
		// }

		out.writeByte(78);
		out.writeShort(anInt3860);
		out.writeByte(anInt3904);

		// if(anIntArray3859 != null) {
		// out.write_byte(79);
		// out.write_short(anInt3900);
		// out.write_short(anInt3905);
		// out.write_byte(anInt3904);
		// out.write_byte(anIntArray3859.length);
		// for(int i = 0; i < anIntArray3859.length; i++)
		// out.write_short(anIntArray3859[i]);
		// }

		// if(aByte3912 == 2) {
		// out.write_byte(81);
		// out.write_byte(anInt3882 == 0 ? 0 : anInt3882 / 256);
		// }

		// if(aBoolean3891)
		// out.write_byte(82);

		// if(!aBoolean3853)
		// out.write_byte(88);

		// if(!aBoolean3895)
		// out.write_byte(89);

		// if(aBoolean3870)
		// out.write_byte(90);

		// if(aBoolean3873)
		// out.write_byte(91);

		// if(aByte3912 == 3) {
		// out.write_byte(93);
		// out.write_short(anInt3882);
		// }

		// if(aByte3912 == 4)
		// out.write_byte(94);
		//
		// if(aByte3912 == 5) {
		// out.write_byte(95);
		// out.write_short(anInt3882);
		// }
		//
		// if(aBoolean3924)
		// out.write_byte(96);
		//
		// if(aBoolean3866)
		// out.write_byte(97);
		//
		// if(aBoolean3923)
		// out.write_byte(98);
		//
		// out.write_byte(99);
		// out.write_byte(anInt3857);
		// out.write_short(anInt3835);
		//
		// out.write_byte(100);
		// out.write_byte(anInt3844);
		// out.write_short(anInt3913);
		//
		// out.write_byte(101);
		// out.write_byte(anInt3850);
		//
		// out.write_byte(102);
		// out.write_short(anInt3838);

		if (thirdInt == 0)
			out.writeByte(103);

		// out.write_byte(104);
		// out.write_byte(anInt3865);
		//
		// if(aBoolean3906)
		// out.write_byte(105);
		//
		// if(animations != null) {
		// out.write_byte(106);
		// out.write_byte(animations.length);
		// for(int i = 0; i < animations.length; i++) {
		// out.write_big_smart(animations[i]);
		// out.write_byte(anIntArray3869[i]);
		// }
		// }
		//
		// out.write_byte(107);
		// out.write_short(anInt3851);
		//
		// if(options != null) {
		// for(int i = 0; i < options.length; i++) {
		// if(options[i] == null)
		// continue;
		// out.write_byte(150 + i);
		// out.write_string(options[i]);
		// }
		// }
		//
		// if(anIntArray3908 != null) {
		// out.write_byte(160);
		// out.write_byte(anIntArray3908.length);
		// for(int i = 0; i < anIntArray3908.length; i++) {
		// out.write_short(anIntArray3908[i]);
		// }
		// }
		//
		// if(aByte3912 == 3) {
		// out.write_byte(162);
		// out.write_int(anInt3882);
		// }
		//
		// out.write_byte(163);
		// out.write_byte(aByte3847);
		// out.write_byte(aByte3849);
		// out.write_byte(aByte3837);
		// out.write_byte(aByte3914);
		//
		// out.write_byte(164);
		// out.write_short(anInt3834);
		//
		// out.write_byte(165);
		// out.write_short(anInt3875);
		//
		// out.write_byte(166);
		// out.write_short(anInt3877);
		//
		// out.write_byte(167);
		// out.write_short(anInt3921);
		//
		// if(aBoolean3894)
		// out.write_byte(168);
		//
		// if(aBoolean3845)
		// out.write_byte(169);
		//
		// out.write_byte(170);
		// out.write_smart(anInt3383);
		//
		// out.write_byte(171);
		// out.write_smart(anInt3362);
		//
		// out.write_byte(173);
		// out.write_short(anInt3302);
		// out.write_short(anInt3336);
		//
		// if(ub)
		// out.write_byte(177);
		//
		// if(db != 0) {
		// out.write_byte(178);
		// out.write_byte(db);
		// }
		//
		// if(bloom)
		// out.write_byte(189);

		if (anIntArray4534 != null) {
			for (int i = 0; i < anIntArray4534.length; i++) {
				if (anIntArray4534[i] == -1 || anIntArray4534[i] == 65535)
					continue;
				out.writeByte(190 + i);
				out.writeShort(anIntArray4534[i]);
			}
		}

		if (parameters != null) {
			out.writeByte(249);
			out.writeByte(parameters.size());
			for (int key : parameters.keySet()) {
				Object value = parameters.get(key);
				boolean string_value = value instanceof String;
				out.writeByte(string_value ? 1 : 0);
				out.write24BitInt(key);
				if (string_value)
					out.writeString((String) value);
				else
					out.writeInt((Integer) value);
			}
		}

		out.writeByte(0);

		byte[] data = new byte[out.getOffset()];
		out.setOffset(0);
		out.getBytes(data, 0, data.length);
		return data;
	}
	
	@Override
	public String toString() {
		return id+" - "+name;
	}

}
