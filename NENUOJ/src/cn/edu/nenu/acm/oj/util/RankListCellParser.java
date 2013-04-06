package cn.edu.nenu.acm.oj.util;

import java.util.LinkedList;
import java.util.List;

public class RankListCellParser {
	
	public static List<RankListCellExpression>  rankListCellExpressions = new LinkedList<RankListCellExpression>();
	/**
	 * build the recognize pattern, base on ISun's virtual-judge work.
	 * @author Winguse
	 */
	static{
		rankListCellExpressions.add(
		new RankListCellExpression(
				new long[][]{{}},
				"No submisson"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{contestLength, 0, 0};
			}
		});//1
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{}},
				"Not solved, with one wrong submission"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{contestLength, 1, 0};
			}
		});//2
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Solved at $1 minute with no wrong submisson"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, 1, 1};
			}
		});//3
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Not solved, with $1 wrong submission(s)"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, 2, 1};
			}
		});//4

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 hour $2 minute with no wrong submisson"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, 1, 1};
			}
		});//5

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 hour $2 minute with one wrong submission"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, 2, 1};
			}
		});//6

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $1 minute with $2 submission(s)"
		){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1], 1};
			}
		});//7
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $1 minute with $2 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1] + 1, 1};
			}
		});//8
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $2 minute with $1 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0], 1};
			}
		});//9
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $2 minute with $1 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0] + 1, 1};
			}
		});//10

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $1 hour $2 minute $3 second with no wrong submission"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, 1, 1};
			}
		});//11
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute with $3 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2], 1};
			}
		});//12
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute with $3 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2] + 1, 1};
			}
		});//13
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 hour $3 minute with $1 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0], 1};
			}
		});//14
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59}},
				"Solved at $2 hour $3 minute with $1 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0] + 1, 1};
			}
		});//15
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute $3 second with $4 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3], 1};
			}
		});//16
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute $3 second with $4 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3] + 1, 1};
			}
		});//17
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $2 hour $3 minute $4 second with $1 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0], 1};
			}
		});//18

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $2 hour $3 minute $4 second with $1 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0] + 1, 1};
			}
		});//19
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Not solved, with $1 wrong submissions, the last one at $2 minute"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0], 0};
			}
		});//20
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 hour $3 minute"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0], 0};
			}
		});//21
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 hour $3 minute $4 second"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0], 0};
			}
		});//22
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Not solved, with $2 wrong submissions, the last one at $1 minute"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1], 0};
			}
		});//23
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Not solved, with $3 wrong submissions, the last one at $1 hour $2 minute"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2], 0};
			}
		});//24		
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Not solved, with $4 wrong submissions, the last one at $1 hour $2 minute $3 second"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3], 0};
			}
		});//25
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Not solved, with one wrong submission, at $1 minute"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L, 1, 0};
			}
		});//26
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with no wrong submisson"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 1, 1};
			}
		});//27
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with one wrong submission"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 2, 1};
			}
		});//28
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with one wrong submission"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 2, 1};
			}
		});//28
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Solved at $1 minute $2 second with $3 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2], 1};
			}
		});//29
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Solved at $1 minute $2 second with $3 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2] + 1, 1};
			}
		});//30
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 minute $3 second with $1 submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0], 1};
			}
		});//31
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 minute $3 second with $1 wrong submission(s)"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0] + 1, 1};
			}
		});//32
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 minute $3 second"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0], 0};
			}
		});//33
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Not solved, with $3 wrong submissions, the last one at $1 minute $2 second"
				){
			public long[] getInfo(int val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2], 0};
			}
		});//34
	}
	
}
