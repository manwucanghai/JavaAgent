package com.zws.agent.inject;

/**
 * @author zhengws
 * @date 2020-03-15 10:31
 */
public class TestInject {
    public static void main(String[] args) {
        callMethod("zhengws", "1.1.1.1");
    }

    private static void callMethod(String name, String address) {
        MethodInject.start(1L, "method", name, address);

        /**
         * mv.visitInsn(Opcodes.ICONST_1);
         * mv.visitLdcInsn("method");
         *
         * mv.visitInsn(Opcodes.ICONST_2);  指定传入数组长度
         * mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");  数组元素类型
         *
         *
         * mv.visitInsn(Opcodes.DUP);
         * mv.visitInsn(Opcodes.ICONST_0);
         * mv.visitVarInsn(Opcodes.ALOAD, 0);
         * mv.visitInsn(Opcodes.AASTORE);
         *
         *
         * mv.visitInsn(Opcodes.DUP);
         * mv.visitInsn(Opcodes.ICONST_1);
         * mv.visitVarInsn(Opcodes.ALOAD, 1);
         * mv.visitInsn(Opcodes.AASTORE);
         *
         * mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/zws/agent/inject/MethodInject", "start", "(ILjava/lang/String;[Ljava/lang/Object;)V", false);
         */

        /**
         * 对应代码：
         *
         * this.visitLdcInsn(mMethodId);
         * this.visitLdcInsn(injectRule.getType());
         * List<ParaNode> pnList = injectRule.getParaNodeList();
         * this.visitIntInsn(Opcodes.BIPUSH, pnList.size());
         * this.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
         * aastoreArray(pnList);
         * this.visitMethodInsn(INVOKESTATIC, InjectClass, "start", "(ILjava/lang/String;[Ljava/lang/Object;)V", false);
         *
         *
         * public void aastoreArray(List<ParaNode> pnList) {
         *         for (int i = 0; i < pnList.size(); i++) {
         *             ParaNode pn = pnList.get(i);
         *             if (pn.getDataType().equals(DataType.STRING)) {
         *                 setString(pn, i);
         *             } else if (pn.getDataType().equals(DataType.INT)) {
         *                 this.setInteger(pn, i);
         *             } else if (pn.getDataType().equals(DataType.LONG)) {
         *                 this.setLong(pn, i);
         *             } else if (pn.getDataType().equals(DataType.CONSTANT)) {
         *                 this.setConstant(pn, i);
         *             } else {
         *                 this.setObject(pn, i);
         *             }
         *         }
         *     }
         *
         * public void setString(ParaNode pn, int i) {
         *   if (ParaType.MTHLD.equals(pn.getType())) {
         *       this.visitInsn(Opcodes.DUP);
         *       this.visitIntInsn(Opcodes.BIPUSH, i);
         *       this.visitVarInsn(Opcodes.ALOAD, pn.getLocation());
         *       this.visitInsn(Opcodes.AASTORE);
         *   } else if (ParaType.FIELD.equals(pn.getType())) {
         *       this.visitInsn(Opcodes.DUP);
         *       this.visitIntInsn(Opcodes.BIPUSH, i);
         *       this.visitVarInsn(Opcodes.ALOAD, 0);
         *       this.visitFieldInsn(Opcodes.GETFIELD, pn.getOwner(), pn.getName(), pn.getDesc());
         *       this.visitInsn(Opcodes.AASTORE);
         *   }
         * }
         */
    }
}
