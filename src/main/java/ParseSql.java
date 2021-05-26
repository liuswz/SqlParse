//import org.antlr.runtime.NoViableAltException;
//
//
///**
// * @ProjectName: Sql
// * @Package: PACKAGE_NAME
// * @ClassName: ParseSql
// * @Author: owen
// * @Description:
// * @Date: 2021/3/29 10:39
// * @Version: 1.0
// */
//public class ParseSql {
//    public ASTNode parse(String command, Context ctx, String viewFullyQualifiedName)
//            throws ParseException {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Parsing command: " + command);
//        }
//        /*
//         * 词法分析，忽略关键字大小写
//         * 输入：一堆字符，这里是HiveSQl
//         * 输出：一串Token，这里是 TokenRewriteStream
//         *
//         * Antlr 对语法文件HiveLexer.g 编译后自动生成的词法解析和语法解析类（HiveLexerX , HiveParser）
//         * 文件 HiveLexer.g 定义了一些hive的关键字，from、where、数字的定义格式【0-9】，分隔符，分隔符，比较符等。
//         * 每个关键字分支都会变成一个token
//         *
//         * HiveLexerX 是antlr 根据词法规则文件，通过编译生成的代码类。
//         * 能够执行词法和语法解析 最终生成一个ASTNode
//         * */
//        HiveLexerX lexer = new HiveLexerX(new ANTLRNoCaseStringStream(command));
//
//
//        //根据词法分析的结果得到tokens，此时不只是单纯的字符串，而是有意义的封装，其本身是一个流。
//        //lexer 把SQL语句中各个语法分支，都转换成底层引擎能识别的各种Token
//        TokenRewriteStream tokens = new TokenRewriteStream(lexer);
//
//
//        if (ctx != null) {
//            if (viewFullyQualifiedName == null) {
//                // Top level query
//                ctx.setTokenRewriteStream(tokens);
//            } else {
//                // It is a view
//                ctx.addViewTokenRewriteStream(viewFullyQualifiedName, tokens);
//            }
//            lexer.setHiveConf(ctx.getConf());
//        }
//
//        /*
//         * 语法解析
//         * HiveParser是Antlr根据HiveParser.g 生成的文件
//         * */
//        HiveParser parser = new HiveParser(tokens);
//        if (ctx != null) {
//            parser.setHiveConf(ctx.getConf());
//        }
//
//        parser.setTreeAdaptor(adaptor);
//        HiveParser.statement_return r = null;
//        try {
//            //转化为AST Tree
//            //并放在ASTNode的Tree属性中，通过getTree方法获取返回
//            r = parser.statement();
//        } catch (RecognitionException e) {
//            e.printStackTrace();
//            throw new ParseException(parser.errors);
//        }
//        if (lexer.getErrors().size() == 0 && parser.errors.size() == 0) {
//            LOG.debug("Parse Completed");
//        } else if (lexer.getErrors().size() != 0) {
//            throw new ParseException(lexer.getErrors());
//        } else {
//            throw new ParseException(parser.errors);
//        }
//
//        //抽象语法树返回
//        ASTNode tree = (ASTNode) r.getTree();
//        System.out.println("tree="+tree.toStringTree());
//        tree.setUnknownTokenBoundaries();
//        return tree;
//    }
//}
