package cn.edu.nenu.acm.oj.test;

import cn.edu.nenu.acm.oj.eto.LoginException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.SubmitException;
import cn.edu.nenu.acm.oj.service.remote.HDUProblemSubmitter;
import cn.edu.nenu.acm.oj.service.remote.IProblemSubmitter;

public class SubmitterTest {

	public static void main(String[] args) {
		IProblemSubmitter submitter = new HDUProblemSubmitter();
		try {
			submitter.setAccountInformation("qiandusikao_cs", "111111");
			submitter.login();
			submitter
					.submit("1000",
							"#include <cstdio>\n\nint main(){\nint a,b;\nwhile(~scanf(\"%d%d\",&a,&b)||true){\nprintf(\"%d\\n\",a+b);\n}\nreturn 0;\n}",
							"g++");
			while (submitter.getResult()) {
				System.out.println(submitter.getStatusDescription());
			}
			System.out.println(submitter.getStatusDescription());
			System.out.println(submitter.getStatus());
			System.out.println(submitter.getMemory());
			System.out.println(submitter.getTime());
			System.out.println(submitter.getRemoteRunId());
			System.out.println(submitter.getAdditionalInformation());
		} catch (SubmitException | NetworkException | LoginException e) {
			e.printStackTrace();
		}
	}

}
