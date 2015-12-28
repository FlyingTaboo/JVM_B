package cz.cvut.run.constants;

public class Constants {
	public static final String JAVA_MAGIC = "0xCAFEBABE";
	
	
	public static final byte TAG_INTEGER = 3;
	public static final byte TAG_FLOAT = 4;
	public static final byte TAG_LONG = 5;
	public static final byte TAG_DOUBLE = 6;
	public static final byte TAG_UTF8 = 1;
	public static final byte TAG_STRING = 8;
	public static final byte TAG_CLASS = 7;
	public static final byte TAG_FIELDREF = 9;
	public static final byte TAG_METHODREF = 10;
	public static final byte TAG_INTERFACE_METHODREF = 11;
	public static final byte TAG_NAME_AND_TYPE = 12;
	
	public static final String INIT = "<init>";


	public static final Object CODE = "Code";
	
	
	
	
	
	
	
	
	
	
	
	
	//===========INSTRUCTIONS=============================
	public static final byte INSTRUCTION_aaload = (byte) (0x32 & 0xFF);
	public static final byte INSTRUCTION_aastore = (byte) (0x53 & 0xFF);
	public static final byte INSTRUCTION_aconst_null = (byte) (0x01 & 0xFF);
	public static final byte INSTRUCTION_aload = (byte) (0x19 & 0xFF);
	public static final byte INSTRUCTION_aload_0 = (byte) (0x2a & 0xFF);
	public static final byte INSTRUCTION_aload_1 = (byte) (0x2b & 0xFF);
	public static final byte INSTRUCTION_aload_2 = (byte) (0x2c & 0xFF);
	public static final byte INSTRUCTION_aload_3 = (byte) (0x2d & 0xFF);
	public static final byte INSTRUCTION_anewarray = (byte) (0xbd & 0xFF);
	public static final byte INSTRUCTION_areturn = (byte) (0xb0 & 0xFF);
	public static final byte INSTRUCTION_arraylength = (byte) (0xbe & 0xFF);
	public static final byte INSTRUCTION_astore = (byte) (0x3a & 0xFF);
	public static final byte INSTRUCTION_astore_0 = (byte) (0x4b & 0xFF);
	public static final byte INSTRUCTION_astore_1 = (byte) (0x4c & 0xFF);
	public static final byte INSTRUCTION_astore_2 = (byte) (0x4d & 0xFF);
	public static final byte INSTRUCTION_astore_3 = (byte) (0x4e & 0xFF);
	public static final byte INSTRUCTION_athrow = (byte) (0xbf & 0xFF);
	public static final byte INSTRUCTION_baload = (byte) (0x33 & 0xFF);
	public static final byte INSTRUCTION_bastore = (byte) (0x54 & 0xFF);
	public static final byte INSTRUCTION_bipush = (byte) (0x10 & 0xFF);
	public static final byte INSTRUCTION_breakpoint = (byte) (0xca & 0xFF);
	public static final byte INSTRUCTION_caload = (byte) (0x34 & 0xFF);
	public static final byte INSTRUCTION_castore = (byte) (0x55 & 0xFF);
	public static final byte INSTRUCTION_checkcast = (byte) (0xc0 & 0xFF);
	public static final byte INSTRUCTION_d2f = (byte) (0x90 & 0xFF);
	public static final byte INSTRUCTION_d2i = (byte) (0x8e & 0xFF);
	public static final byte INSTRUCTION_d2l = (byte) (0x8f & 0xFF);
	public static final byte INSTRUCTION_dadd = (byte) (0x63 & 0xFF);
	public static final byte INSTRUCTION_daload = (byte) (0x31 & 0xFF);
	public static final byte INSTRUCTION_dastore = (byte) (0x52 & 0xFF);
	public static final byte INSTRUCTION_dcmpg = (byte) (0x98 & 0xFF);
	public static final byte INSTRUCTION_dcmpl = (byte) (0x97 & 0xFF);
	public static final byte INSTRUCTION_dconst_0 = (byte) (0x0e & 0xFF);
	public static final byte INSTRUCTION_dconst_1 = (byte) (0x0f & 0xFF);
	public static final byte INSTRUCTION_ddiv = (byte) (0x6f & 0xFF);
	public static final byte INSTRUCTION_dload = (byte) (0x18 & 0xFF);
	public static final byte INSTRUCTION_dload_0 = (byte) (0x26 & 0xFF);
	public static final byte INSTRUCTION_dload_1 = (byte) (0x27 & 0xFF);
	public static final byte INSTRUCTION_dload_2 = (byte) (0x28 & 0xFF);
	public static final byte INSTRUCTION_dload_3 = (byte) (0x29 & 0xFF);
	public static final byte INSTRUCTION_dmul = (byte) (0x6b & 0xFF);
	public static final byte INSTRUCTION_dneg = (byte) (0x77 & 0xFF);
	public static final byte INSTRUCTION_drem = (byte) (0x73 & 0xFF);
	public static final byte INSTRUCTION_dreturn = (byte) (0xaf & 0xFF);
	public static final byte INSTRUCTION_dstore = (byte) (0x39 & 0xFF);
	public static final byte INSTRUCTION_dstore_0 = (byte) (0x47 & 0xFF);
	public static final byte INSTRUCTION_dstore_1 = (byte) (0x48 & 0xFF);
	public static final byte INSTRUCTION_dstore_2 = (byte) (0x49 & 0xFF);
	public static final byte INSTRUCTION_dstore_3 = (byte) (0x4a & 0xFF);
	public static final byte INSTRUCTION_dsub = (byte) (0x67 & 0xFF);
	public static final byte INSTRUCTION_dup = (byte) (0x59 & 0xFF);
	public static final byte INSTRUCTION_dup_x1 = (byte) (0x5a & 0xFF);
	public static final byte INSTRUCTION_dup_x2 = (byte) (0x5b & 0xFF);
	public static final byte INSTRUCTION_dup2 = (byte) (0x5c & 0xFF);
	public static final byte INSTRUCTION_dup2_x1 = (byte) (0x5d & 0xFF);
	public static final byte INSTRUCTION_dup2_x2 = (byte) (0x5e & 0xFF);
	public static final byte INSTRUCTION_f2d = (byte) (0x8d & 0xFF);
	public static final byte INSTRUCTION_f2i = (byte) (0x8b & 0xFF);
	public static final byte INSTRUCTION_f2l = (byte) (0x8c & 0xFF);
	public static final byte INSTRUCTION_fadd = (byte) (0x62 & 0xFF);
	public static final byte INSTRUCTION_faload = (byte) (0x30 & 0xFF);
	public static final byte INSTRUCTION_fastore = (byte) (0x51 & 0xFF);
	public static final byte INSTRUCTION_fcmpg = (byte) (0x96 & 0xFF);
	public static final byte INSTRUCTION_fcmpl = (byte) (0x95 & 0xFF);
	public static final byte INSTRUCTION_fconst_0 = (byte) (0x0b & 0xFF);
	public static final byte INSTRUCTION_fconst_1 = (byte) (0x0c & 0xFF);
	public static final byte INSTRUCTION_fconst_2 = (byte) (0x0d & 0xFF);
	public static final byte INSTRUCTION_fdiv = (byte) (0x6e & 0xFF);
	public static final byte INSTRUCTION_fload = (byte) (0x17 & 0xFF);
	public static final byte INSTRUCTION_fload_0 = (byte) (0x22 & 0xFF);
	public static final byte INSTRUCTION_fload_1 = (byte) (0x23 & 0xFF);
	public static final byte INSTRUCTION_fload_2 = (byte) (0x24 & 0xFF);
	public static final byte INSTRUCTION_fload_3 = (byte) (0x25 & 0xFF);
	public static final byte INSTRUCTION_fmul = (byte) (0x6a & 0xFF);
	public static final byte INSTRUCTION_fneg = (byte) (0x76 & 0xFF);
	public static final byte INSTRUCTION_frem = (byte) (0x72 & 0xFF);
	public static final byte INSTRUCTION_freturn = (byte) (0xae & 0xFF);
	public static final byte INSTRUCTION_fstore = (byte) (0x38 & 0xFF);
	public static final byte INSTRUCTION_fstore_0 = (byte) (0x43 & 0xFF);
	public static final byte INSTRUCTION_fstore_1 = (byte) (0x44 & 0xFF);
	public static final byte INSTRUCTION_fstore_2 = (byte) (0x45 & 0xFF);
	public static final byte INSTRUCTION_fstore_3 = (byte) (0x46 & 0xFF);
	public static final byte INSTRUCTION_fsub = (byte) (0x66 & 0xFF);
	public static final byte INSTRUCTION_getfield = (byte) (0xb4 & 0xFF);
	public static final byte INSTRUCTION_getstatic = (byte) (0xb2 & 0xFF);
	public static final byte INSTRUCTION_goto = (byte) (0xa7 & 0xFF);
	public static final byte INSTRUCTION_goto_w = (byte) (0xc8 & 0xFF);
	public static final byte INSTRUCTION_i2b = (byte) (0x91 & 0xFF);
	public static final byte INSTRUCTION_i2c = (byte) (0x92 & 0xFF);
	public static final byte INSTRUCTION_i2d = (byte) (0x87 & 0xFF);
	public static final byte INSTRUCTION_i2f = (byte) (0x86 & 0xFF);
	public static final byte INSTRUCTION_i2l = (byte) (0x85 & 0xFF);
	public static final byte INSTRUCTION_i2s = (byte) (0x93 & 0xFF);
	public static final byte INSTRUCTION_iadd = (byte) (0x60 & 0xFF);
	public static final byte INSTRUCTION_iaload = (byte) (0x2e & 0xFF);
	public static final byte INSTRUCTION_iand = (byte) (0x7e & 0xFF);
	public static final byte INSTRUCTION_iastore = (byte) (0x4f & 0xFF);
	public static final byte INSTRUCTION_iconst_m1 = (byte) (0x2 & 0xFF);
	public static final byte INSTRUCTION_iconst_0 = (byte) (0x3 & 0xFF);
	public static final byte INSTRUCTION_iconst_1 = (byte) (0x4 & 0xFF);
	public static final byte INSTRUCTION_iconst_2 = (byte) (0x5 & 0xFF);
	public static final byte INSTRUCTION_iconst_3 = (byte) (0x6 & 0xFF);
	public static final byte INSTRUCTION_iconst_4 = (byte) (0x7 & 0xFF);
	public static final byte INSTRUCTION_iconst_5 = (byte) (0x8 & 0xFF);
	public static final byte INSTRUCTION_idiv = (byte) (0x6c & 0xFF);
	public static final byte INSTRUCTION_if_acmpeq = (byte) (0xa5 & 0xFF);
	public static final byte INSTRUCTION_if_acmpne = (byte) (0xa6 & 0xFF);
	public static final byte INSTRUCTION_if_icmpeq = (byte) (0x9f & 0xFF);
	public static final byte INSTRUCTION_if_icmpge = (byte) (0xa2 & 0xFF);
	public static final byte INSTRUCTION_if_icmpgt = (byte) (0xa3 & 0xFF);
	public static final byte INSTRUCTION_if_icmple = (byte) (0xa4 & 0xFF);
	public static final byte INSTRUCTION_if_icmplt = (byte) (0xa1 & 0xFF);
	public static final byte INSTRUCTION_if_icmpne = (byte) (0xa0 & 0xFF);
	public static final byte INSTRUCTION_ifeq = (byte) (0x99 & 0xFF);
	public static final byte INSTRUCTION_ifge = (byte) (0x9c & 0xFF);
	public static final byte INSTRUCTION_ifgt = (byte) (0x9d & 0xFF);
	public static final byte INSTRUCTION_ifle = (byte) (0x9e & 0xFF);
	public static final byte INSTRUCTION_iflt = (byte) (0x9b & 0xFF);
	public static final byte INSTRUCTION_ifne = (byte) (0x9a & 0xFF);
	public static final byte INSTRUCTION_ifnonnull = (byte) (0xc7 & 0xFF);
	public static final byte INSTRUCTION_ifnull = (byte) (0xc6 & 0xFF);
	public static final byte INSTRUCTION_iinc = (byte) (0x84 & 0xFF);
	public static final byte INSTRUCTION_iload = (byte) (0x15 & 0xFF);
	public static final byte INSTRUCTION_iload_0 = (byte) (0x1a & 0xFF);
	public static final byte INSTRUCTION_iload_1 = (byte) (0x1b & 0xFF);
	public static final byte INSTRUCTION_iload_2 = (byte) (0x1c & 0xFF);
	public static final byte INSTRUCTION_iload_3 = (byte) (0x1d & 0xFF);
	public static final byte INSTRUCTION_impdep1 = (byte) (0xfe & 0xFF);
	public static final byte INSTRUCTION_impdep2 = (byte) (0xff & 0xFF);
	public static final byte INSTRUCTION_imul = (byte) (0x68 & 0xFF);
	public static final byte INSTRUCTION_ineg = (byte) (0x74 & 0xFF);
	public static final byte INSTRUCTION_instanceof = (byte) (0xc1 & 0xFF);
	public static final byte INSTRUCTION_invokedynamic = (byte) (0xba & 0xFF);
	public static final byte INSTRUCTION_invokeinterface = (byte) (0xb9 & 0xFF);
	public static final byte INSTRUCTION_invokespecial = (byte) (0xb7 & 0xFF);
	public static final byte INSTRUCTION_invokestatic = (byte) (0xb8 & 0xFF);
	public static final byte INSTRUCTION_invokevirtual = (byte) (0xb6 & 0xFF);
	public static final byte INSTRUCTION_ior = (byte) (0x80 & 0xFF);
	public static final byte INSTRUCTION_irem = (byte) (0x70 & 0xFF);
	public static final byte INSTRUCTION_ireturn = (byte) (0xac & 0xFF);
	public static final byte INSTRUCTION_ishl = (byte) (0x78 & 0xFF);
	public static final byte INSTRUCTION_ishr = (byte) (0x7a & 0xFF);
	public static final byte INSTRUCTION_istore = (byte) (0x36 & 0xFF);
	public static final byte INSTRUCTION_istore_0 = (byte) (0x3b & 0xFF);
	public static final byte INSTRUCTION_istore_1 = (byte) (0x3c & 0xFF);
	public static final byte INSTRUCTION_istore_2 = (byte) (0x3d & 0xFF);
	public static final byte INSTRUCTION_istore_3 = (byte) (0x3e & 0xFF);
	public static final byte INSTRUCTION_isub = (byte) (0x64 & 0xFF);
	public static final byte INSTRUCTION_iushr = (byte) (0x7c & 0xFF);
	public static final byte INSTRUCTION_ixor = (byte) (0x82 & 0xFF);
	public static final byte INSTRUCTION_jsr = (byte) (0xa8 & 0xFF);
	public static final byte INSTRUCTION_jsr_w = (byte) (0xc9 & 0xFF);
	public static final byte INSTRUCTION_l2d = (byte) (0x8a & 0xFF);
	public static final byte INSTRUCTION_l2f = (byte) (0x89 & 0xFF);
	public static final byte INSTRUCTION_l2i = (byte) (0x88 & 0xFF);
	public static final byte INSTRUCTION_ladd = (byte) (0x61 & 0xFF);
	public static final byte INSTRUCTION_laload = (byte) (0x2f & 0xFF);
	public static final byte INSTRUCTION_land = (byte) (0x7f & 0xFF);
	public static final byte INSTRUCTION_lastore = (byte) (0x50 & 0xFF);
	public static final byte INSTRUCTION_lcmp = (byte) (0x94 & 0xFF);
	public static final byte INSTRUCTION_lconst_0 = (byte) (0x9 & 0xFF);
	public static final byte INSTRUCTION_lconst_1 = (byte) (0x0a & 0xFF);
	public static final byte INSTRUCTION_ldc = (byte) (0x12 & 0xFF);
	public static final byte INSTRUCTION_ldc_w = (byte) (0x13 & 0xFF);
	public static final byte INSTRUCTION_ldc2_w = (byte) (0x14 & 0xFF);
	public static final byte INSTRUCTION_ldiv = (byte) (0x6d & 0xFF);
	public static final byte INSTRUCTION_lload = (byte) (0x16 & 0xFF);
	public static final byte INSTRUCTION_lload_0 = (byte) (0x1e & 0xFF);
	public static final byte INSTRUCTION_lload_1 = (byte) (0x1f & 0xFF);
	public static final byte INSTRUCTION_lload_2 = (byte) (0x20 & 0xFF);
	public static final byte INSTRUCTION_lload_3 = (byte) (0x21 & 0xFF);
	public static final byte INSTRUCTION_lmul = (byte) (0x69 & 0xFF);
	public static final byte INSTRUCTION_lneg = (byte) (0x75 & 0xFF);
	public static final byte INSTRUCTION_lookupswitch = (byte) (0xab & 0xFF);
	public static final byte INSTRUCTION_lor = (byte) (0x81 & 0xFF);
	public static final byte INSTRUCTION_lrem = (byte) (0x71 & 0xFF);
	public static final byte INSTRUCTION_lreturn = (byte) (0xad & 0xFF);
	public static final byte INSTRUCTION_lshl = (byte) (0x79 & 0xFF);
	public static final byte INSTRUCTION_lshr = (byte) (0x7b & 0xFF);
	public static final byte INSTRUCTION_lstore = (byte) (0x37 & 0xFF);
	public static final byte INSTRUCTION_lstore_0 = (byte) (0x3f & 0xFF);
	public static final byte INSTRUCTION_lstore_1 = (byte) (0x40 & 0xFF);
	public static final byte INSTRUCTION_lstore_2 = (byte) (0x41 & 0xFF);
	public static final byte INSTRUCTION_lstore_3 = (byte) (0x42 & 0xFF);
	public static final byte INSTRUCTION_lsub = (byte) (0x65 & 0xFF);
	public static final byte INSTRUCTION_lushr = (byte) (0x7d & 0xFF);
	public static final byte INSTRUCTION_lxor = (byte) (0x83 & 0xFF);
	public static final byte INSTRUCTION_monitorenter = (byte) (0xc2 & 0xFF);
	public static final byte INSTRUCTION_monitorexit = (byte) (0xc3 & 0xFF);
	public static final byte INSTRUCTION_multianewarray = (byte) (0xc5 & 0xFF);
	public static final byte INSTRUCTION_new = (byte) (0xbb & 0xFF);
	public static final byte INSTRUCTION_newarray = (byte) (0xbc & 0xFF);
	public static final byte INSTRUCTION_nop = (byte) (0x00 & 0xFF);
	public static final byte INSTRUCTION_pop = (byte) (0x57 & 0xFF);
	public static final byte INSTRUCTION_pop2 = (byte) (0x58 & 0xFF);
	public static final byte INSTRUCTION_putfield = (byte) (0xb5 & 0xFF);
	public static final byte INSTRUCTION_putstatic = (byte) (0xb3 & 0xFF);
	public static final byte INSTRUCTION_ret = (byte) (0xa9 & 0xFF);
	public static final byte INSTRUCTION_return = (byte) (0xb1 & 0xFF);
	public static final byte INSTRUCTION_saload = (byte) (0x35 & 0xFF);
	public static final byte INSTRUCTION_sastore = (byte) (0x56 & 0xFF);
	public static final byte INSTRUCTION_sipush = (byte) (0x11 & 0xFF);
	public static final byte INSTRUCTION_swap = (byte) (0x5f & 0xFF);
	public static final byte INSTRUCTION_tableswitch = (byte) (0xaa & 0xFF);
	public static final byte INSTRUCTION_wide = (byte) (0xc4 & 0xFF);


	public static final Object MAIN = "main";


	public static final Object LINE_NUMBER_TABLE = "LineNumberTable";


	public static final Object LOCAL_VARIABLE_TABLE = "LocalVariableTable";
	
	
}
