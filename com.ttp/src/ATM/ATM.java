package ATM;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();

        showMain(accounts);
    }

    public static void showMain(ArrayList<Account> accounts) {
        while (true) {
            System.out.println("欢迎来到系统操作页面");
            System.out.println("1.开户");
            System.out.println("2.登录");
            System.out.println("请输入对应代码来进行操作：");
            Scanner sc = new Scanner(System.in);
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    // 开户
                    register(sc, accounts);
                    break;
                case 2:
                    // 登录
                    login(sc, accounts);
                    break;
                default:
                    System.out.println("请输入正确的指令");
            }
        }
    }

    private static void login(Scanner sc, ArrayList<Account> accounts) {
        if (accounts.size() == 0) {
            System.out.println("系统内无任何账号 ，请先注册");
            return;
        }
        System.out.println("用户登录界面");
        while (true) {
            System.out.println("请输入卡号：");
            String cardId = sc.next();
            Account acc = getAccountByCardId(accounts, cardId);
            if (acc != null) {
                while (true) {
                    System.out.println("请输入密码：");
                    String password = sc.next();
                    if (password.equals(acc.getPassword())) {
                        System.out.println("登录成功!" + acc.getName() + "男士/女士，您的卡号是：" + acc.getCardId() + ", 欢迎进入本系统");
                        showAccount(sc, acc, accounts);
                        return;
                    } else {
                        System.out.println("密码错误");
                    }
                }
            } else {
                System.out.println("卡号不存在");
            }
        }
    }

    private static void showAccount(Scanner sc, Account acc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("=====================用户界面===========================");
            System.out.println("请输入相对应的指令来进行操作");
            System.out.println("1.用户个人资料");
            System.out.println("2.转账");
            System.out.println("3.存款");
            System.out.println("4.取款");
            System.out.println("5.退出");
            System.out.println("6.注销账户");
            System.out.println("7.修改密码");
            System.out.println("请输入指令：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //用户个人资料
                    showUser(acc);
                    break;
                case 2:
                    //转账
                    transfer(sc, acc, accounts);
                    break;
                case 3:
                    //存款
                    deposit(sc, acc);
                    break;
                case 4:
                    //取款
                    withdraw(sc, acc);
                    break;
                case 5:
                    //退出
                    System.out.println("欢迎再来");
                    return;
                case 6:
                    //注销账户
                    logout(acc, accounts, sc);
                    break;
                case 7:
                    //修改密码
                    changePassword(sc, acc);
                    break;
                default:
                    System.out.println("请输入正确的指令");
            }
        }

    }

    private static void changePassword(Scanner sc, Account acc) {
        while (true) {
            System.out.println("请输入账户原有密码");
            String password = sc.next();
            if (acc.getPassword().equals(password)) {
                while (true) {
                    System.out.println("请输入要更换的密码：");
                    String okPassword = sc.next();
                    System.out.println("请再次输入密码");
                    String Password = sc.next();
                    if (okPassword.equals(Password)){
                        acc.setPassword(okPassword);
                        System.out.println("密码已修改！");
                        return;
                    }else{
                        System.out.println("两次密码不一致!");
                    }
                }

            } else {

                OUT:
                while (true) {
                    System.out.println("密码错误，是否继续操作？");
                    System.out.println("1.是");
                    System.out.println("2.否");
                    int command = sc.nextInt();
                    switch (command) {
                        case 1:
                            break OUT;
                        case 2:
                            System.out.println("已退出该界面");
                            return;
                        default:
                            System.out.println("指令有误");
                    }
                }

            }
        }

    }

    private static void logout(Account acc, ArrayList<Account> accounts, Scanner sc) {
        if (acc.getMoney() != 0) {
            System.out.println("无法注销 ，请先取出账户余额，您的余额为：" + acc.getMoney());
            return;
        }

        while (true) {
            System.out.println("您是否要注销账户，一旦注销无法恢复");
            System.out.println("1.确认");
            System.out.println("2.取消");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    while (true) {
                        // 确认
                        System.out.println("请输入账户密码以继续操作");
                        String password = sc.next();
                        if (acc.getPassword().equals(password)) {
                            accounts.remove(acc);
                            System.out.println("=========账户已注销==========");
                            showMain(accounts);
                        } else {
                            OUT:
                            while (true) {
                                System.out.println("密码错误，是否退出？");
                                System.out.println("1.是");
                                System.out.println("2.否");
                                System.out.println("请输入指令以进行操作");
                                int inCommand = sc.nextInt();
                                switch (inCommand) {
                                    case 1:
                                        System.out.println("已退出操作系统");
                                        return;
                                    case 2:
                                        break OUT;
                                    default:
                                        System.out.println("指令错误");
                                        ;
                                }
                            }

                        }

                    }
                case 2:
                    // 取消
                    System.out.println("已退出操作系统");
                    return;
                default:
                    System.out.println("错误指令");
            }
        }

    }

    private static void withdraw(Scanner sc, Account acc) {
        if (acc.getMoney() >= 100) {
            while (true) {
                System.out.println("请输入您要取款的金额");
                double outMoney = sc.nextDouble();
                if (acc.getMoney() >= outMoney) {
                    if (acc.getQuotaMoney() >= outMoney) {
                        while (true) {
                            System.out.println("您是否要取款：" + outMoney + "元");
                            System.out.println("1.确认");
                            System.out.println("2.取消");
                            System.out.println("3.退出");
                            System.out.println("请输入指令进行操作");
                            int command = sc.nextInt();
                            switch (command) {
                                case 1:
                                    // 确认
                                    acc.setMoney(acc.getMoney() - outMoney);
                                    System.out.println("转账成功 ， 账户余额：" + acc.getMoney());
                                    return;
                                case 2:
                                    // 取消
                                    System.out.println("已取消取款操作");
                                    break;
                                case 3:
                                    // 退出
                                    System.out.println("已退出取款系统");
                                    return;
                                default:
                                    System.out.println("请输入正确的指令");
                            }
                        }
                    } else {
                        System.out.println("金额已超过当次限额 ， 账户的当次限额为：" + acc.getQuotaMoney());
                    }
                } else {
                    System.out.println("对不起，余额不足，账号尚余金额：" + acc.getMoney());
                }
            }
        } else {
            System.out.println("余额不足100 ，无法取款");
        }
    }

    private static void transfer(Scanner sc, Account acc, ArrayList<Account> accounts) {
        if (accounts.size() < 2) {
            System.out.println("系统账号不足2个 ， 请先注册");
            return;
        }

        if (acc.getMoney() < 100) {
            System.out.println("账户余额不足100 ， 无法转账");
            return;
        }

        while (true) {
            String cardId = null;
            while (true) {
                System.out.println("=================转账界面==================");
                System.out.println("请输入转账对象卡号：");
                cardId = sc.next();
                if (acc.getCardId().equals(cardId)) {
                    System.out.println("您不能转给自己!");
                } else {
                    break;
                }
            }

            Account otherAccount = getAccountByCardId(accounts, cardId);
            if (otherAccount != null) {
                while (true) {
                    System.out.println("请输入转账金额");
                    double transferMoney = sc.nextDouble();
                    if (acc.getMoney() >= transferMoney) {
                        OUT:
                        while (true) {
                            System.out.println("请确认是否转账：" + transferMoney + "给" + otherAccount.getName());
                            System.out.println("1. 确认");
                            System.out.println("2. 取消");
                            System.out.println("3. 退出");
                            int command = sc.nextInt();
                            switch (command) {
                                case 1:
                                    // 确认
                                    otherAccount.setMoney(transferMoney += otherAccount.getMoney());
                                    acc.setMoney(acc.getMoney() - transferMoney);
                                    System.out.println("转账成功！ 您的账户余额尚余：" + acc.getMoney());
                                    return;
                                case 2:
                                    // 取消
                                    System.out.println("已取消转账");
                                    break OUT;
                                case 3:
                                    // 退出
                                    System.out.println("已退出转账系统 ，欢迎再来");
                                    return;
                                default:
                                    System.out.println("指令错误");
                            }
                        }
                    } else {
                        System.out.println("账户余额不足 ， 您的账户余额为：" + acc.getMoney());
                    }
                }
            } else {
                System.out.println("不存在该卡号用户");
            }
        }

    }

    private static void deposit(Scanner sc, Account acc) {
        while (true) {
            System.out.println("======================存款界面======================");
            System.out.println("请输入存款金额");
            double money = sc.nextDouble();

            OUT:
            while (true) {
                System.out.println("您是否要存入：" + money);
                System.out.println("1.确认");
                System.out.println("2.取消");
                System.out.println("3.退出");
                System.out.println("请输入对应指令进行操作");
                int command = sc.nextInt();
                switch (command) {
                    case 1:
                        // 确认
                        acc.setMoney(money += acc.getMoney());
                        System.out.println("存款:" + money + "成功! 余额尚余：" + acc.getMoney());
                        return;
                    case 2:
                        // 取消
                        System.out.println("存款操作已取消");
                        break OUT;
                    case 3:
                        // 退出
                        System.out.println("已退出存款界面");
                        return;
                    default:
                        System.out.println("请输入正确指令");
                }
            }
        }

    }

    private static void showUser(Account acc) {
        System.out.println("====================用户个人信息=====================");
        System.out.println("用户名：" + acc.getName());
        System.out.println("卡号：" + acc.getCardId());
        System.out.println("余额：" + acc.getMoney());
        System.out.println("当次余额：" + acc.getQuotaMoney());
        System.out.println("==================================================");
    }

    private static void register(Scanner sc, ArrayList<Account> accounts) {
        System.out.println("欢迎来到系统开户界面");
        System.out.println("请输入用户名");
        String name = sc.next();
        String password = "";
        while (true) {
            System.out.println("请输入密码");
            password = sc.next();
            System.out.println("请再次输入密码");
            String okPassword = sc.next();
            if (okPassword.equals(password)) {
                break;
            } else {
                System.out.println("两次密码不相同");
            }
        }
        String cardId = createCardId(accounts);
        System.out.println("请输入当次限额");
        Double quotaMoney = sc.nextDouble();

        Account acc = new Account(name, password, cardId, quotaMoney);
        accounts.add(acc);

        System.out.println("账户注册成功，您的卡号是：" + cardId + "请千万保管好");
    }

    private static String createCardId(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }
            Account acc = getAccountByCardId(accounts, cardId);
            if (acc == null) {
                return cardId;
            }
        }
    }

    private static Account getAccountByCardId(ArrayList<Account> accounts, String cardId) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;
    }
}
